package com.judahben149.eclair.core.ml

import com.judahben149.eclair.domain.model.EquipmentDetectionResult

expect class EquipmentClassifier {
    suspend fun initialize(): Result<Unit>
    suspend fun classify(imageData: Any): Result<EquipmentDetectionResult?>
    fun close()
    fun isReady(): Boolean
}
