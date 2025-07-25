package com.judahben149.eclair.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.judahben149.eclair.data.local.dto.ChatMessageDto
import com.judahben149.eclair.data.local.dto.ConversationDto

internal expect object EclairDatabaseCtor : RoomDatabaseConstructor<EclairDatabase>

@Database(entities = [ConversationDto::class, ChatMessageDto::class], version = 1)
@ConstructedBy(EclairDatabaseCtor::class)
abstract class EclairDatabase: RoomDatabase() {
    abstract fun chatDao(): ChatDao
}