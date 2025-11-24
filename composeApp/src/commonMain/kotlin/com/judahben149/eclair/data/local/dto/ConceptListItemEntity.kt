package com.judahben149.eclair.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "concept_list_items")
data class ConceptListItemEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String?,
    val displayOrder: Int?,
    val published: Boolean?,
    val updatedAt: String,
    val version: Int
)
