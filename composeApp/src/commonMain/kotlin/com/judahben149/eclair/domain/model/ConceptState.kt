package com.judahben149.eclair.domain.model

import com.judahben149.eclair.data.remote.dto.Concept

sealed class ConceptState {
    data object Loading : ConceptState()
    data class Cached(val concept: Concept) : ConceptState()
    data class Fresh(val concept: Concept) : ConceptState()
    data class UpdateAvailable(val current: Concept?, val new: Concept) : ConceptState()
    data class Error(val exception: Exception) : ConceptState()
}
