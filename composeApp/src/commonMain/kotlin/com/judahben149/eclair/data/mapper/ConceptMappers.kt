package com.judahben149.eclair.data.mapper

import com.judahben149.eclair.data.local.dto.ConceptEntity
import com.judahben149.eclair.data.remote.dto.Concept
import com.judahben149.eclair.data.remote.dto.Section
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

fun Concept.toEntity(): ConceptEntity {
    return ConceptEntity(
        id = id,
        title = title,
        description = description,
        updatedAt = updatedAt,
        version = version,
        sectionsJson = json.encodeToString(sections ?: emptyList())
    )
}

fun ConceptEntity.toConcept(): Concept {
    return Concept(
        id = id,
        title = title,
        description = description,
        updatedAt = updatedAt,
        version = version,
        sections = if (sectionsJson.isNotEmpty()) {
            json.decodeFromString<List<Section>>(sectionsJson)
        } else {
            emptyList()
        }
    )
}
