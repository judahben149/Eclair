package com.judahben149.eclair.presentation.screens.train

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.judahben149.eclair.data.local.ConceptListDao
import com.judahben149.eclair.data.mapper.toConceptListItem
import com.judahben149.eclair.data.mapper.toEntity
import com.judahben149.eclair.data.remote.api.ConceptApiService
import com.judahben149.eclair.data.remote.dto.ConceptListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ConceptListState {
    data object Loading : ConceptListState()
    data class Success(val concepts: List<ConceptListItem>) : ConceptListState()
    data class Error(val message: String) : ConceptListState()
}

class TrainHomeViewModel(
    private val conceptApiService: ConceptApiService,
    private val conceptListDao: ConceptListDao
) : ViewModel() {

    private val _conceptListState = MutableStateFlow<ConceptListState>(ConceptListState.Loading)
    val conceptListState: StateFlow<ConceptListState> = _conceptListState.asStateFlow()

    init {
        loadConcepts()
    }

    fun loadConcepts() {
        viewModelScope.launch {
            _conceptListState.value = ConceptListState.Loading

            // 1. First, load from database (offline-first)
            try {
                val cachedConcepts = conceptListDao.getAllConcepts()
                if (cachedConcepts.isNotEmpty()) {
                    val concepts = cachedConcepts
                        .map { it.toConceptListItem() }
                        .sortedBy { it.displayOrder ?: Int.MAX_VALUE }
                    _conceptListState.value = ConceptListState.Success(concepts)
                }
            } catch (e: Exception) {
                // If database read fails, continue to network fetch
            }

            // 2. Then, fetch from network and update
            try {
                val remoteConcepts = conceptApiService.getAllConcepts()

                // Save to database
                conceptListDao.insertAll(remoteConcepts.map { it.toEntity() })

                // Update UI with fresh data
                val sortedConcepts = remoteConcepts.sortedBy { it.displayOrder ?: Int.MAX_VALUE }
                _conceptListState.value = ConceptListState.Success(sortedConcepts)
            } catch (e: Exception) {
                // If network fails but we have cached data, keep showing cached data
                val currentState = _conceptListState.value
                if (currentState !is ConceptListState.Success) {
                    // Only show error if we don't have any cached data
                    _conceptListState.value = ConceptListState.Error(
                        e.message ?: "Failed to load concepts"
                    )
                }
            }
        }
    }

    fun retry() {
        loadConcepts()
    }
}
