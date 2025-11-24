package com.judahben149.eclair.data.repository

import com.judahben149.eclair.data.local.ConceptDao
import com.judahben149.eclair.data.mapper.toConcept
import com.judahben149.eclair.data.mapper.toEntity
import com.judahben149.eclair.data.remote.api.ConceptApiService
import com.judahben149.eclair.data.remote.dto.Concept
import com.judahben149.eclair.domain.model.ConceptState
import com.judahben149.eclair.domain.repository.ConceptRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ConceptRepositoryImpl(
    private val api: ConceptApiService,
    private val dao: ConceptDao
) : ConceptRepository {

    override suspend fun getConceptWithUpdateCheck(id: Int): Flow<ConceptState> = flow {
        // 1. Emit cached version immediately for instant display
        val cached = dao.getConceptById(id)
        if (cached != null) {
            emit(ConceptState.Cached(cached.toConcept()))
        } else {
            emit(ConceptState.Loading)
        }

        // 2. Fetch from network in background
        try {
            val remote = api.getConceptById(id)
            val localVersion = cached?.version ?: 0

            // 3. Compare versions
            if (remote.version > localVersion) {
                emit(ConceptState.UpdateAvailable(
                    current = cached?.toConcept(),
                    new = remote
                ))
            } else if (cached == null) {
                // First time fetch - save and emit
                dao.insertConcept(remote.toEntity())
                emit(ConceptState.Fresh(remote))
            }
        } catch (e: Exception) {
            if (cached == null) {
                emit(ConceptState.Error(e))
            }
            // If cached exists, keep showing it (already emitted)
        }
    }

    override suspend fun acceptUpdate(concept: Concept) {
        dao.updateConcept(concept.toEntity())
    }
}
