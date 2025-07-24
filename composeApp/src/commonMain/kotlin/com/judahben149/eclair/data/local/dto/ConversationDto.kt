package com.judahben149.eclair.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ConversationDto(
    @PrimaryKey(autoGenerate = false)
    val id: String = generateUUID(),
    val title: String,
    val timeCreated: Long = getCurrentTimeMillis(),
    val timeUpdated: Long
)
