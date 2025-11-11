package com.judahben149.eclair.domain.model

data class EquipmentDetectionResult(
    val equipmentName: String,
    val confidence: Float,
    val category: EquipmentCategory,
    val alternatives: List<AlternativeDetection> = emptyList()
)

data class AlternativeDetection(
    val equipmentName: String,
    val confidence: Float,
    val category: EquipmentCategory
)

enum class EquipmentCategory {
    LIGHT,
    CONTROLLER,
    CABLE,
    DIMMER,
    FIXTURE,
    ACCESSORY,
    UNKNOWN
}
