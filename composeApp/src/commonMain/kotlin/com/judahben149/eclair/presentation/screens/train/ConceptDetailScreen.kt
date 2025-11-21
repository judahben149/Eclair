package com.judahben149.eclair.presentation.screens.train

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.judahben149.eclair.data.remote.dto.Section
import com.judahben149.eclair.presentation.screens.train.components.ConceptImage
import com.judahben149.eclair.presentation.screens.train.components.ImageViewerDialog
import com.judahben149.eclair.presentation.screens.train.components.MarkdownText
import com.judahben149.eclair.presentation.screens.train.components.TableOfContentsDialog
import com.judahben149.eclair.presentation.screens.train.components.UpdateAvailableDialog
import kotlinx.coroutines.launch

// Sealed class for content items
sealed class ContentItem {
    data class SectionHeading(val heading: String, val section: Section) : ContentItem()
    data class MarkdownHeading(val text: String, val level: Int) : ContentItem()
    data class ContentData(val type: String, val value: String) : ContentItem()
}

// Extract markdown headings from text
fun extractMarkdownHeadings(markdown: String): List<Pair<String, Int>> {
    val headingRegex = """^(#{1,6})\s+(.+)$""".toRegex(RegexOption.MULTILINE)
    return headingRegex.findAll(markdown).map {
        it.groupValues[2].trim() to it.groupValues[1].length
    }.toList()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConceptDetailScreen(
    viewModel: ConceptDetailViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val showUpdateDialog by viewModel.showUpdateDialog.collectAsState()
    var showTableOfContents by remember { mutableStateOf(false) }
    var selectedImageIndex by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (uiState is UiState.Success) {
                            (uiState as UiState.Success).concept.title
                        } else {
                            ""
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    if (uiState is UiState.Success) {
                        IconButton(onClick = { showTableOfContents = true }) {
                            Icon(
                                Icons.AutoMirrored.Filled.List,
                                contentDescription = "Table of Contents"
                            )
                        }
                    }
                },
                windowInsets = WindowInsets(0, 0, 0, 0)
            )
        }
    ) { padding ->
        when (val state = uiState) {
            is UiState.Loading -> {
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Success -> {
                val scope = rememberCoroutineScope()
                val lazyListState = rememberLazyListState()

                // Collect all image URLs from the concept
                val allImages = remember(state.concept) {
                    buildList {
                        state.concept.sections?.forEach { section ->
                            section.content.forEach { item ->
                                if (item.type == "image") {
                                    add(item.value)
                                }
                            }
                        }
                    }
                }

                // Build list of content items with headings
                val contentItems = remember(state.concept) {
                    buildList {
                        state.concept.sections?.forEach { section ->
                            add(ContentItem.SectionHeading(section.heading, section))
                            section.content.forEach { item ->
                                // Extract markdown headings from text content
                                if (item.type == "text") {
                                    val headings = extractMarkdownHeadings(item.value)
                                    headings.forEach { (text, level) ->
                                        add(ContentItem.MarkdownHeading(text, level))
                                    }
                                }
                                add(ContentItem.ContentData(item.type, item.value))
                            }
                        }
                    }
                }

                ConceptContentLazy(
                    contentItems = contentItems,
                    allImages = allImages,
                    onImageClick = { imageUrl ->
                        selectedImageIndex = allImages.indexOf(imageUrl)
                    },
                    lazyListState = lazyListState,
                    modifier = Modifier.padding(padding)
                )

                // Table of Contents
                if (showTableOfContents) {
                    TableOfContentsDialog(
                        concept = state.concept,
                        onHeadingClick = { heading ->
                            // Find the index of the heading in contentItems
                            val index = contentItems.indexOfFirst { item ->
                                when (item) {
                                    is ContentItem.SectionHeading -> item.heading == heading
                                    is ContentItem.MarkdownHeading -> item.text == heading
                                    else -> false
                                }
                            }

                            if (index >= 0) {
                                scope.launch {
                                    lazyListState.animateScrollToItem(index)
                                }
                            }
                            showTableOfContents = false
                        },
                        onDismiss = { showTableOfContents = false }
                    )
                }

                // Image Viewer
                selectedImageIndex?.let { index ->
                    if (allImages.isNotEmpty()) {
                        ImageViewerDialog(
                            images = allImages,
                            initialIndex = index,
                            onDismiss = { selectedImageIndex = null }
                        )
                    }
                }
            }
            is UiState.Error -> {
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Error: ${state.message}")
                }
            }
        }
    }

    // Update dialog
    showUpdateDialog?.let { dialog ->
        UpdateAvailableDialog(
            currentVersion = dialog.currentVersion,
            newVersion = dialog.newVersion,
            onAccept = dialog.onAccept,
            onDecline = dialog.onDecline
        )
    }
}

@Composable
fun ConceptContentLazy(
    contentItems: List<ContentItem>,
    allImages: List<String>,
    onImageClick: (String) -> Unit,
    lazyListState: androidx.compose.foundation.lazy.LazyListState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = lazyListState,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title
//        item {
//            Text(
//                text = title,
//                style = MaterialTheme.typography.headlineLarge,
//                modifier = Modifier.padding(bottom = 8.dp)
//            )
//        }

        // Description
//        if (description != null) {
//            item {
//                Text(
//                    text = description,
//                    style = MaterialTheme.typography.bodyLarge,
//                    color = MaterialTheme.colorScheme.onSurfaceVariant,
//                    modifier = Modifier.padding(bottom = 16.dp)
//                )
//            }
//        }


        items(contentItems) { item ->
            when (item) {
                is ContentItem.SectionHeading -> {
                    Text(
                        text = item.heading,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 24.dp, bottom = 12.dp)
                    )
                }
                is ContentItem.MarkdownHeading -> {
                    // Skip - headings are already rendered inside MarkdownText
                }
                is ContentItem.ContentData -> {
                    when (item.type) {
                        "text" -> {
                            MarkdownText(
                                markdown = item.value,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                        "image" -> {
                            ConceptImage(
                                imageUrl = item.value,
                                onClick = { onImageClick(item.value) },
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
