package com.judahben149.eclair.domain.repository

import com.judahben149.eclair.data.remote.dto.Concept
import com.judahben149.eclair.domain.model.ConceptState
import kotlinx.coroutines.flow.Flow

interface ConceptRepository {
    suspend fun getConceptWithUpdateCheck(id: Int): Flow<ConceptState>
    suspend fun acceptUpdate(concept: Concept)
}
