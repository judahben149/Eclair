package com.judahben149.eclair.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "concepts")
data class ConceptEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String?,
    val updatedAt: String,
    val version: Int,
    val sectionsJson: String // JSON string of sections array
)
