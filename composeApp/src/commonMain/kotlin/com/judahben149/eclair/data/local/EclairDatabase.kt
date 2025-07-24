package com.judahben149.eclair.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.judahben149.eclair.data.local.dto.ChatMessageDto
import com.judahben149.eclair.data.local.dto.ConversationDto

@ConstructedBy(EclairDatabaseConstructor::class)
@Database(entities = [ConversationDto::class, ChatMessageDto::class], version = 1)
abstract class EclairDatabase: RoomDatabase() {
    abstract fun chatDao(): ChatDao
}