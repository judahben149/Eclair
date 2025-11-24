package com.judahben149.eclair.core.ml

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.judahben149.eclair.domain.model.AlternativeDetection
import com.judahben149.eclair.domain.model.EquipmentCategory
import com.judahben149.eclair.domain.model.EquipmentDetectionResult
import com.judahben149.eclair.util.logIt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import java.nio.ByteBuffer
import java.nio.ByteOrder

actual class EquipmentClassifier(private val context: Context) {
    private var interpreter: Interpreter? = null
    private var labels: List<String> = emptyList()
    private var isInitialized = false

    companion object {
        private const val MODEL_NAME = "equipment_classifier2.tflite"
        private const val EQUIPMENT_LABELS_NAME = "lighting_labels.txt"
        private const val IMAGENET_LABELS_NAME = "imagenet_labels.txt"
        private const val CONFIDENCE_THRESHOLD = 0.7f
        private const val MAX_ALTERNATIVES = 3
    }

    actual suspend fun initialize(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            try {
                val modelBuffer = FileUtil.loadMappedFile(context, MODEL_NAME)
                interpreter = Interpreter(modelBuffer)
                Log.d("Eclair", "Model found oooooo")

            } catch (e: Exception) {
                // Model not found - this is expected for now
                // We'll use mock data instead
                Log.d("Eclair", "Model not found. Using mock data.")
            }

            // Load labels - for now use hardcoded labels
            labels = loadLabels()
            isInitialized = true
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun loadLabels(): List<String> {
        // Priority order:
        // 1. Try equipment_labels.txt (for custom trained model)
        // 2. Fall back to imagenet_labels.txt (for testing with MobileNet)
        // 3. Fall back to hardcoded equipment labels (last resort)

        // Try equipment labels first (for custom model)
        try {
            val equipmentLabels = context.assets.open(EQUIPMENT_LABELS_NAME)
                .bufferedReader()
                .use { it.readLines() }
                .filter { it.isNotBlank() }

            if (equipmentLabels.isNotEmpty()) {
                "Loaded ${equipmentLabels.size} equipment labels from $EQUIPMENT_LABELS_NAME".logIt("Classifier")
                return equipmentLabels
            }
        } catch (e: Exception) {
            "Equipment labels file not found or invalid: ${e.message}".logIt("Classifier")
        }

        // Try ImageNet labels (for testing with standard MobileNet)
        try {
            val imagenetLabels = context.assets.open(IMAGENET_LABELS_NAME)
                .bufferedReader()
                .use { it.readLines() }
                .filter { it.isNotBlank() }

            if (imagenetLabels.isNotEmpty()) {
                "Loaded ${imagenetLabels.size} ImageNet labels from $IMAGENET_LABELS_NAME".logIt("Classifier")
                return imagenetLabels
            }
        } catch (e: Exception) {
            "ImageNet labels file not found or invalid: ${e.message}".logIt("Classifier")
        }

        // Fall back to hardcoded labels
        "Using hardcoded equipment labels as fallback".logIt("Classifier")
        return listOf(
            "LED Par Light",
            "Moving Head Light",
            "Fresnel Light",
            "DMX Controller",
            "Lighting Console",
            "XLR Cable",
            "DMX Cable",
            "Power Cable",
            "Dimmer Pack",
            "Light Stand",
            "Clamp",
            "Color Gel"
        )
    }

    actual suspend fun classify(imageData: Any): Result<EquipmentDetectionResult?> =
        withContext(Dispatchers.Default) {
            try {
                "classify() called".logIt("Classifier")

                if (!isInitialized) {
                    "Classifier not initialized".logIt("Classifier")
                    return@withContext Result.failure(
                        IllegalStateException("Classifier not initialized")
                    )
                }

                val bitmap = imageData as? Bitmap
                if (bitmap == null) {
                    "Invalid image data: not a Bitmap".logIt("Classifier")
                    return@withContext Result.failure(
                        IllegalArgumentException("Invalid image data")
                    )
                }

                "Valid bitmap received, starting inference".logIt("Classifier")

                // Run inference with the real model
                val result = runInference(bitmap)
                "classify() returning result: ${result?.equipmentName ?: "null"}".logIt("Classifier")
                Result.success(result)
            } catch (e: Exception) {
                "Exception in classify(): ${e.message}".logIt("Classifier")
                e.printStackTrace()
                Result.failure(e)
            }
        }

    private fun runInference(bitmap: Bitmap): EquipmentDetectionResult? {
        try {
            "Starting runInference".logIt("Classifier")

            val interpreter = this.interpreter ?: run {
                "Interpreter is null, returning null".logIt("Classifier")
                return null
            }

            "Interpreter ready, bitmap size: ${bitmap.width}x${bitmap.height}".logIt("Classifier")

            // Get model tensor info
            val inputTensor = interpreter.getInputTensor(0)
            val outputTensor = interpreter.getOutputTensor(0)
            val inputShape = inputTensor.shape()
            val outputShape = outputTensor.shape()
            val inputType = inputTensor.dataType()
            val outputType = outputTensor.dataType()

            "Input: shape=${inputShape.contentToString()}, type=$inputType".logIt("Classifier")
            "Output: shape=${outputShape.contentToString()}, type=$outputType".logIt("Classifier")

            // Get the expected input size from the model
            val modelInputSize = inputShape[1]  // Assumes [1, height, width, 3]
            "Model expects input size: ${modelInputSize}x${modelInputSize}".logIt("Classifier")

            // Determine number of classes from output shape
            val numClasses = when (outputShape.size) {
                2 -> outputShape[1]  // Classification: [1, num_classes]
                3 -> {
                    // Detection model: [1, num_classes, num_boxes] or similar
                    "ERROR: This appears to be a detection model, not classification!".logIt("Classifier")
                    "Expected output shape: [1, num_classes]".logIt("Classifier")
                    "Got output shape: ${outputShape.contentToString()}".logIt("Classifier")
                    return null
                }
                else -> {
                    "ERROR: Unexpected output shape: ${outputShape.contentToString()}".logIt("Classifier")
                    return null
                }
            }

            "Model expects $numClasses output classes, we have ${labels.size} labels".logIt("Classifier")

            // Preprocess image - use the size the model expects
            "Preprocessing image...".logIt("Classifier")

            // Resize bitmap to model input size
            val resizedBitmap = Bitmap.createScaledBitmap(
                bitmap,
                modelInputSize,
                modelInputSize,
                true
            )
            "Image preprocessed to ${modelInputSize}x${modelInputSize}".logIt("Classifier")

            // Convert to float array - NO NORMALIZATION (YOLOv11 TFLite expects raw pixel values 0-255)
            val inputArray = Array(1) { Array(modelInputSize) { Array(modelInputSize) { FloatArray(3) } } }

            for (y in 0 until modelInputSize) {
                for (x in 0 until modelInputSize) {
                    val pixel = resizedBitmap.getPixel(x, y)
                    // Extract RGB as raw values (0-255)
                    inputArray[0][y][x][0] = ((pixel shr 16) and 0xFF).toFloat()  // R
                    inputArray[0][y][x][1] = ((pixel shr 8) and 0xFF).toFloat()   // G
                    inputArray[0][y][x][2] = (pixel and 0xFF).toFloat()           // B
                }
            }
            "Input array created (raw pixel values 0-255)".logIt("Classifier")

            // Prepare output buffer based on output type
            val isQuantized = outputType.toString().contains("UINT8")
            val outputArray = Array(1) { FloatArray(numClasses) }

            // Run inference
            "Running inference...".logIt("Classifier")
            interpreter.run(inputArray, outputArray)
            "Inference completed successfully".logIt("Classifier")

            // Get probabilities from output array
            val probabilities = outputArray[0]
            "Got ${probabilities.size} probabilities".logIt("Classifier")

            // Log top 5 predictions with labels
            val topPredictions = probabilities
                .mapIndexed { index, prob -> index to prob }
                .sortedByDescending { it.second }
                .take(5)

            topPredictions.forEachIndexed { i, (idx, prob) ->
                val label = if (idx < labels.size) labels[idx] else "unknown"
                "Top ${i + 1}: $label (class=$idx), confidence=${(prob * 100).toInt()}%".logIt("Classifier")
            }

            // Log confidence threshold check
            val topConf = topPredictions.first().second
            if (topConf < CONFIDENCE_THRESHOLD) {
                "⚠️ Top confidence ($topConf) below threshold ($CONFIDENCE_THRESHOLD)".logIt("Classifier")
            }

            "Processing inference results...".logIt("Classifier")
            val result = processInferenceResults(probabilities)
            "Result: ${result?.equipmentName ?: "null"}".logIt("Classifier")

            return result
        } catch (e: Exception) {
            "Error in runInference: ${e.message}".logIt("Classifier")
            e.printStackTrace()
            return null
        }
    }

    private fun processInferenceResults(probabilities: FloatArray): EquipmentDetectionResult? {
        "Processing inference results with ${probabilities.size} probabilities".logIt("Classifier")

        // Get top predictions
        val predictions = probabilities
            .mapIndexed { index, prob -> index to prob }
            .sortedByDescending { it.second }

        val topPrediction = predictions.first()
        "Top prediction: class=${topPrediction.first}, confidence=${topPrediction.second}".logIt("Classifier")

        // Check if confidence meets threshold
        if (topPrediction.second < CONFIDENCE_THRESHOLD) {
            "Confidence ${topPrediction.second} below threshold $CONFIDENCE_THRESHOLD".logIt("Classifier")
            return null
        }

        // Map class index to label - handle case where we have more classes than labels
        val getLabelForIndex: (Int) -> String = { index ->
            if (index < labels.size) {
                labels[index]
            } else {
                // For untrained MobileNet, just use class index
                "Class_$index"
            }
        }

        // Get alternatives
        val alternatives = predictions
            .drop(1)
            .take(MAX_ALTERNATIVES)
            .map { (index, confidence) ->
                val label = getLabelForIndex(index)
                AlternativeDetection(
                    equipmentName = label,
                    confidence = confidence,
                    category = categorizeEquipment(label)
                )
            }

        val equipmentName = getLabelForIndex(topPrediction.first)
        val result = EquipmentDetectionResult(
            equipmentName = equipmentName,
            confidence = topPrediction.second,
            category = categorizeEquipment(equipmentName),
            alternatives = alternatives
        )

        "Created result: $equipmentName with ${result.alternatives.size} alternatives".logIt("Classifier")
        return result
    }

//    private fun createMockDetectionResult(): EquipmentDetectionResult {
//        // Mock detection result for testing UI
//        return EquipmentDetectionResult(
//            equipmentName = "LED Par Light",
//            confidence = 0.87f,
//            category = EquipmentCategory.LIGHT,
//            alternatives = listOf(
//                AlternativeDetection(
//                    equipmentName = "Moving Head Light",
//                    confidence = 0.68f,
//                    category = EquipmentCategory.LIGHT
//                ),
//                AlternativeDetection(
//                    equipmentName = "Fresnel Light",
//                    confidence = 0.45f,
//                    category = EquipmentCategory.LIGHT
//                )
//            )
//        )
//    }

    private fun categorizeEquipment(name: String): EquipmentCategory {
        return when {
            name.contains("Light", ignoreCase = true) -> EquipmentCategory.LIGHT
            name.contains("Controller", ignoreCase = true) ||
            name.contains("Console", ignoreCase = true) -> EquipmentCategory.CONTROLLER
            name.contains("Cable", ignoreCase = true) -> EquipmentCategory.CABLE
            name.contains("Dimmer", ignoreCase = true) -> EquipmentCategory.DIMMER
            name.contains("Stand", ignoreCase = true) ||
            name.contains("Clamp", ignoreCase = true) -> EquipmentCategory.ACCESSORY
            else -> EquipmentCategory.UNKNOWN
        }
    }

    actual fun close() {
        interpreter?.close()
        interpreter = null
        isInitialized = false
    }

    actual fun isReady(): Boolean = isInitialized
}
