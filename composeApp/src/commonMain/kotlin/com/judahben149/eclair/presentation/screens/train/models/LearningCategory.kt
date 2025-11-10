package com.judahben149.eclair.presentation.screens.train.models

import androidx.compose.ui.graphics.Color
import com.judahben149.eclair.navigation.CategoryType

data class LearningCategory(
    val type: CategoryType,
    val title: String,
    val description: String,
    val colorStart: Color,
    val colorEnd: Color,
    val pattern: PatternType
)

enum class PatternType {
    CIRCLES,
    WAVES,
    CURTAINS,
    GEARS,
    PRISM,
    CIRCUITS
}

fun getAllCategories(): List<LearningCategory> = listOf(
    LearningCategory(
        type = CategoryType.STAGE_BASICS,
        title = "Stage Basics",
        description = "Learn the fundamentals of stage lighting",
        colorStart = Color(0xFF4A90E2),
        colorEnd = Color(0xFF357ABD),
        pattern = PatternType.CIRCLES
    ),
    LearningCategory(
        type = CategoryType.CONCERT_LIGHTING,
        title = "Concert Lighting",
        description = "Master concert and live event lighting",
        colorStart = Color(0xFF9B59B6),
        colorEnd = Color(0xFF8E44AD),
        pattern = PatternType.WAVES
    ),
    LearningCategory(
        type = CategoryType.THEATRE,
        title = "Theatre",
        description = "Explore theatrical lighting techniques",
        colorStart = Color(0xFFE74C3C),
        colorEnd = Color(0xFFC0392B),
        pattern = PatternType.CURTAINS
    ),
    LearningCategory(
        type = CategoryType.EQUIPMENT,
        title = "Equipment",
        description = "Know your lighting equipment",
        colorStart = Color(0xFFE67E22),
        colorEnd = Color(0xFFD35400),
        pattern = PatternType.GEARS
    ),
    LearningCategory(
        type = CategoryType.COLOUR_THEORY,
        title = "Colour Theory",
        description = "Understand color in lighting design",
        colorStart = Color(0xFFFF6B6B),
        colorEnd = Color(0xFFFF6B6B),
        pattern = PatternType.PRISM
    ),
    LearningCategory(
        type = CategoryType.DMX,
        title = "DMX",
        description = "Learn DMX control and protocols",
        colorStart = Color(0xFF27AE60),
        colorEnd = Color(0xFF229954),
        pattern = PatternType.CIRCUITS
    )
)
