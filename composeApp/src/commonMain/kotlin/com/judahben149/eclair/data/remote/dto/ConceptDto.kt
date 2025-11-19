package com.judahben149.eclair.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ConceptListItem(
    val id: Int,
    val title: String,
    val description: String? = null,
    val displayOrder: Int? = null,
    val published: Boolean? = null,
    val updatedAt: String,
    val version: Int
)

@Serializable
data class Concept(
    val id: Int,
    val title: String,
    val description: String? = null,
    val sections: List<Section>? = null,
    val updatedAt: String,
    val version: Int
)

@Serializable
data class Section(
    val id: Int? = null,
    val heading: String,
    val content: List<ContentItem>
)

@Serializable
data class ContentItem(
    val type: String, // "text" or "image"
    val value: String  // Markdown text or Cloudinary image URL
)
