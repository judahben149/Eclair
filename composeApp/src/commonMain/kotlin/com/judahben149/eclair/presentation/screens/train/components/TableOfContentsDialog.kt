package com.judahben149.eclair.presentation.screens.train.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.judahben149.eclair.data.remote.dto.Concept

data class MarkdownHeading(val text: String, val level: Int)

fun extractHeadings(markdown: String): List<MarkdownHeading> {
    val headingRegex = """^(#{1,6})\s+(.+)$""".toRegex(RegexOption.MULTILINE)
    return headingRegex.findAll(markdown).map {
        MarkdownHeading(
            text = it.groupValues[2].trim(),
            level = it.groupValues[1].length + 1 // +1 because section heading is level 1
        )
    }.toList()
}

@Composable
fun TableOfContentsDialog(
    concept: Concept,
    onHeadingClick: (String) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Table of Contents",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                val tocItems = remember(concept) {
                    buildList {
                        concept.sections?.forEach { section ->
                            // Main section heading (level 1)
                            add(MarkdownHeading(section.heading, 1))

                            // Extract sub-headings from markdown text content
                            section.content
                                .filter { it.type == "text" }
                                .forEach { contentItem ->
                                    addAll(extractHeadings(contentItem.value))
                                }
                        }
                    }
                }

                LazyColumn {
                    items(tocItems) { heading ->
                        TableOfContentsItem(
                            heading = heading.text,
                            level = heading.level,
                            onClick = { onHeadingClick(heading.text) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TableOfContentsItem(
    heading: String,
    level: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Indentation with line for sub-headings
        if (level > 1) {
            Spacer(modifier = Modifier.width(((level - 1) * 16).dp))
            Canvas(
                modifier = Modifier
                    .width(2.dp)
                    .height(24.dp)
            ) {
                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    strokeWidth = 4f
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        Text(
            text = heading,
            style = when (level) {
                1 -> MaterialTheme.typography.titleMedium
                2 -> MaterialTheme.typography.titleSmall
                else -> MaterialTheme.typography.bodyMedium
            }
        )
    }
}
