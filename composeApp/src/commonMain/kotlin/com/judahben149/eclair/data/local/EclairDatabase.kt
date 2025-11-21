package com.judahben149.eclair.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.judahben149.eclair.data.local.dto.ChatMessageDto
import com.judahben149.eclair.data.local.dto.ConceptEntity
import com.judahben149.eclair.data.local.dto.ConceptListItemEntity
import com.judahben149.eclair.data.local.dto.ConversationDto

internal expect object EclairDatabaseCtor : RoomDatabaseConstructor<EclairDatabase>

@Database(
    entities = [
        ConversationDto::class,
        ChatMessageDto::class,
        ConceptEntity::class,
        ConceptListItemEntity::class
    ],
    version = 2
)
@ConstructedBy(EclairDatabaseCtor::class)
abstract class EclairDatabase: RoomDatabase() {
    abstract fun chatDao(): ChatDao
    abstract fun conceptDao(): ConceptDao
    abstract fun conceptListDao(): ConceptListDao
}