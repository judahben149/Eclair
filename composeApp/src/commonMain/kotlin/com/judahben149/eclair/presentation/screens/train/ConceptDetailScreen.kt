package com.judahben149.eclair.presentation.screens.train

import androidx.compose.foundation.layout.Box
import com.judahben149.eclair.presentation.screens.train.components.ConceptImage
import com.judahben149.eclair.presentation.screens.train.components.MarkdownText
import com.judahben149.eclair.presentation.screens.train.components.TableOfContentsDialog
import com.judahben149.eclair.presentation.screens.train.components.UpdateAvailableDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.judahben149.eclair.data.remote.dto.Concept
import com.judahben149.eclair.data.remote.dto.Section

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConceptDetailScreen(
    viewModel: ConceptDetailViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val showUpdateDialog by viewModel.showUpdateDialog.collectAsState()
    var showTableOfContents by remember { mutableStateOf(false) }

    Scaffold(
        contentWindowInsets = androidx.compose.foundation.layout.WindowInsets(0, 0, 0, 0),
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
                }
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
                ConceptContent(
                    concept = state.concept,
                    modifier = Modifier.padding(padding)
                )

                // Table of Contents
                if (showTableOfContents) {
                    TableOfContentsDialog(
                        concept = state.concept,
                        onHeadingClick = { heading ->
                            // Scroll to heading (basic implementation)
                            showTableOfContents = false
                        },
                        onDismiss = { showTableOfContents = false }
                    )
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
fun ConceptContent(
    concept: Concept,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Title
        Text(
            text = concept.title,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Description
        concept.description?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Sections - PRESERVE ORDER!
        concept.sections?.forEach { section ->
            SectionView(section = section)
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun SectionView(section: Section) {
    Column {
        // Section heading
        Text(
            text = section.heading,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Content items - PRESERVE ORDER!
        section.content.forEach { contentItem ->
            when (contentItem.type) {
                "text" -> {
                    MarkdownText(
                        markdown = contentItem.value,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                "image" -> {
                    ConceptImage(
                        imageUrl = contentItem.value,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }
        }
    }
}
