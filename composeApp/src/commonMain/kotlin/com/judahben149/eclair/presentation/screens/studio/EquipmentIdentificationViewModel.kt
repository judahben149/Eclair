package com.judahben149.eclair.presentation.screens.studio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.judahben149.eclair.core.ml.EquipmentClassifier
import com.judahben149.eclair.domain.model.EquipmentDetectionResult
import com.judahben149.eclair.util.getCurrentTimeMillis
import com.judahben149.eclair.util.logIt
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class EquipmentIdentificationState(
    val isLoading: Boolean = false,
    val isClassifierReady: Boolean = false,
    val detectionResult: EquipmentDetectionResult? = null,
    val error: String? = null,
    val showBottomSheet: Boolean = false,
    val capturedImage: Any? = null
)

class EquipmentIdentificationViewModel(
    private val classifier: EquipmentClassifier
) : ViewModel() {

    private val _state = MutableStateFlow(EquipmentIdentificationState())
    val state: StateFlow<EquipmentIdentificationState> = _state.asStateFlow()

    private var analysisJob: Job? = null
    private var lastAnalysisTime = 0L
    private val analysisInterval = 500L

    init {
        initializeClassifier()
    }

    private fun initializeClassifier() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            classifier.initialize()
                .onSuccess {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isClassifierReady = true
                        )
                    }
                    "Classifier initialized successfully".logIt()
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Failed to initialize classifier"
                        )
                    }
                    "Classifier NOT initialized".logIt()
                }
        }
    }

    fun onFrameAnalyzed(imageData: Any) {
        val currentTime = getCurrentTimeMillis()

        // Throttle analysis to avoid overwhelming the system
        if (currentTime - lastAnalysisTime < analysisInterval) {
            return
        }

        lastAnalysisTime = currentTime

        analysisJob?.cancel()

        analysisJob = viewModelScope.launch {
            if (!classifier.isReady()) return@launch

            classifier.classify(imageData)
                .onSuccess { result ->
                    result?.let {
                        _state.update { state ->
                            state.copy(
                                detectionResult = it,
                                showBottomSheet = true,
                                error = null
                            )
                        }
                    }
                }
                .onFailure { error ->
                    // Silent fail for frame analysis - don't show errors
                    // Only log for debugging
                }
        }
    }

    fun onCaptureImage(imageData: Any) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, capturedImage = imageData) }

            classifier.classify(imageData)
                .onSuccess { result ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            detectionResult = result,
                            showBottomSheet = result != null,
                            error = if (result == null) "No equipment detected" else null
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Classification failed"
                        )
                    }
                }
        }
    }

    fun onDismissBottomSheet() {
        _state.update {
            it.copy(
                showBottomSheet = false,
                detectionResult = null
            )
        }
    }

    fun onConfirmSelection() {
        // Navigate to equipment details or save the detection
        // This will be implemented based on navigation structure
        _state.update {
            it.copy(
                showBottomSheet = false
            )
        }
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }

    override fun onCleared() {
        super.onCleared()
        classifier.close()
    }
}