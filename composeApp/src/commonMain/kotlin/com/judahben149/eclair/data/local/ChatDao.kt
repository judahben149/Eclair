package com.judahben149.eclair.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.judahben149.eclair.data.local.dto.ChatMessageDto

@Dao
interface ChatDao {

    @Upsert
    suspend fun insert(chat: ChatMessageDto): Long

    @Query("SELECT * FROM chatmessagedto WHERE conversationId = :conversationId")
    suspend fun getChatByConversationId(conversationId: Int): ChatMessageDto?

    @Query("SELECT * FROM chatmessagedto")
    suspend fun getAllChats(): List<ChatMessageDto>

    @Query("DELETE FROM chatmessagedto WHERE id = :id")
    suspend fun deleteById(id: Int): Int

    @Delete
    suspend fun delete(chat: ChatMessageDto)
}