package com.judahben149.eclair.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.judahben149.eclair.data.local.dto.ConceptEntity

@Dao
interface ConceptDao {
    @Query("SELECT * FROM concepts WHERE id = :id")
    suspend fun getConceptById(id: Int): ConceptEntity?

    @Query("SELECT version FROM concepts WHERE id = :id")
    suspend fun getConceptVersion(id: Int): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConcept(concept: ConceptEntity)

    @Update
    suspend fun updateConcept(concept: ConceptEntity)

    @Query("SELECT * FROM concepts")
    suspend fun getAllConcepts(): List<ConceptEntity>
}
