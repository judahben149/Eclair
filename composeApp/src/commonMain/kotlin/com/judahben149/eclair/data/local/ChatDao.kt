package com.judahben149.eclair.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.judahben149.eclair.data.local.dto.ChatMessageDto
import com.judahben149.eclair.data.local.dto.ConversationDto
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {

    // INSERT
    @Upsert
    suspend fun insertChat(chat: ChatMessageDto): Long

    // FETCH
    @Query("SELECT * FROM chatmessagedto WHERE conversationId = :conversationId")
    suspend fun getChatByConversationId(conversationId: String): ChatMessageDto?

    @Query("SELECT * FROM chatmessagedto")
    suspend fun getAllChats(): List<ChatMessageDto>

    // OBSERVE
    @Query("SELECT * FROM chatmessagedto WHERE conversationId = :conversationId")
    fun observeAllChats(conversationId: String): Flow<List<ChatMessageDto>>


    @Query("SELECT * FROM conversationdto")
    fun observeAllConversations(): Flow<List<ConversationDto>>

    // DELETE
    @Query("DELETE FROM chatmessagedto WHERE id = :id")
    suspend fun deleteById(id: Int): Int

    @Delete
    suspend fun delete(chat: ChatMessageDto)
}