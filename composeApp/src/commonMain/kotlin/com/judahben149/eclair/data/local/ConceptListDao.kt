package com.judahben149.eclair.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.judahben149.eclair.data.local.dto.ConceptListItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConceptListDao {
    @Query("SELECT * FROM concept_list_items ORDER BY displayOrder ASC")
    fun getAllConceptsFlow(): Flow<List<ConceptListItemEntity>>

    @Query("SELECT * FROM concept_list_items ORDER BY displayOrder ASC")
    suspend fun getAllConcepts(): List<ConceptListItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(concepts: List<ConceptListItemEntity>)

    @Query("DELETE FROM concept_list_items")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM concept_list_items")
    suspend fun getCount(): Int
}
