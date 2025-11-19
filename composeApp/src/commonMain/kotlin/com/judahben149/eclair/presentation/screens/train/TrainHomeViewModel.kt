package com.judahben149.eclair.presentation.screens.train

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val conceptApiService: ConceptApiService
) : ViewModel() {

    private val _conceptListState = MutableStateFlow<ConceptListState>(ConceptListState.Loading)
    val conceptListState: StateFlow<ConceptListState> = _conceptListState.asStateFlow()

    init {
        loadConcepts()
    }

    fun loadConcepts() {
        viewModelScope.launch {
            _conceptListState.value = ConceptListState.Loading
            try {
                val concepts = conceptApiService.getAllConcepts()
                    .sortedBy { it.displayOrder ?: Int.MAX_VALUE }
                _conceptListState.value = ConceptListState.Success(concepts)
            } catch (e: Exception) {
                _conceptListState.value = ConceptListState.Error(
                    e.message ?: "Failed to load concepts"
                )
            }
        }
    }

    fun retry() {
        loadConcepts()
    }
}
