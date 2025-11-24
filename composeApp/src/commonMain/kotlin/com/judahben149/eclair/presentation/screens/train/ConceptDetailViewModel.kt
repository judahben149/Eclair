package com.judahben149.eclair.presentation.screens.train

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.judahben149.eclair.data.remote.dto.Concept
import com.judahben149.eclair.domain.model.ConceptState
import com.judahben149.eclair.domain.repository.ConceptRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UiState {
    data object Loading : UiState()
    data class Success(val concept: Concept) : UiState()
    data class Error(val message: String) : UiState()
}

data class UpdateDialogState(
    val currentVersion: Int,
    val newVersion: Int,
    val onAccept: () -> Unit,
    val onDecline: () -> Unit
)

class ConceptDetailViewModel(
    private val repository: ConceptRepository,
    private val conceptId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _showUpdateDialog = MutableStateFlow<UpdateDialogState?>(null)
    val showUpdateDialog: StateFlow<UpdateDialogState?> = _showUpdateDialog.asStateFlow()

    init {
        loadConcept()
    }

    private fun loadConcept() {
        viewModelScope.launch {
            repository.getConceptWithUpdateCheck(conceptId).collect { state ->
                when (state) {
                    is ConceptState.Loading -> {
                        _uiState.value = UiState.Loading
                    }
                    is ConceptState.Cached -> {
                        _uiState.value = UiState.Success(state.concept)
                    }
                    is ConceptState.Fresh -> {
                        _uiState.value = UiState.Success(state.concept)
                    }
                    is ConceptState.UpdateAvailable -> {
                        // Show current content but display update dialog
                        state.current?.let {
                            _uiState.value = UiState.Success(it)
                        }
                        _showUpdateDialog.value = UpdateDialogState(
                            currentVersion = state.current?.version ?: 0,
                            newVersion = state.new.version,
                            onAccept = {
                                viewModelScope.launch {
                                    repository.acceptUpdate(state.new)
                                    _uiState.value = UiState.Success(state.new)
                                    _showUpdateDialog.value = null
                                }
                            },
                            onDecline = {
                                _showUpdateDialog.value = null
                            }
                        )
                    }
                    is ConceptState.Error -> {
                        _uiState.value = UiState.Error(state.exception.message ?: "Unknown error")
                    }
                }
            }
        }
    }
}
