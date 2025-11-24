package com.judahben149.eclair.presentation.screens.train.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

/**
 * Simple markdown renderer that handles basic markdown syntax
 * without external dependencies.
 *
 * Supported:
 * - Headers (# ## ###)
 * - Bold (**text**)
 * - Italic (*text*)
 * - Code (`code`)
 * - Lists (- item)
 * - Horizontal rules (---)
 */
@Composable
fun SimpleMarkdownText(
    markdown: String,
    modifier: Modifier = Modifier
) {
    val codeBackgroundColor = MaterialTheme.colorScheme.surfaceVariant

    Column(modifier = modifier.fillMaxWidth()) {
        val lines = markdown.lines()
        var i = 0

        while (i < lines.size) {
            val line = lines[i].trim()

            when {
                // Skip empty lines
                line.isEmpty() -> {
                    Spacer(modifier = Modifier.height(4.dp))
                }

                // Horizontal rule
                line.startsWith("---") || line.startsWith("***") -> {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                }

                // Headers (sized smaller to distinguish from section headings)
                line.startsWith("# ") -> {
                    Text(
                        text = line.removePrefix("# "),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
                    )
                }
                line.startsWith("## ") -> {
                    Text(
                        text = line.removePrefix("## "),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 10.dp, bottom = 4.dp)
                    )
                }
                line.startsWith("### ") -> {
                    Text(
                        text = line.removePrefix("### "),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                    )
                }
                line.startsWith("#### ") -> {
                    Text(
                        text = line.removePrefix("#### "),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 6.dp, bottom = 4.dp)
                    )
                }

                // List items
                line.startsWith("- ") || line.startsWith("* ") -> {
                    Text(
                        text = buildAnnotatedString {
                            append("• ")
                            appendStyled(line.substring(2), codeBackgroundColor)
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 8.dp, top = 2.dp, bottom = 2.dp)
                    )
                }

                // Regular paragraph
                else -> {
                    Text(
                        text = buildAnnotatedString {
                            appendStyled(line, codeBackgroundColor)
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            }
            i++
        }
    }
}

private fun androidx.compose.ui.text.AnnotatedString.Builder.appendStyled(
    text: String,
    codeBackgroundColor: Color
) {
    var remaining = text

    while (remaining.isNotEmpty()) {
        when {
            // Bold **text**
            remaining.startsWith("**") -> {
                val endIndex = remaining.indexOf("**", 2)
                if (endIndex != -1) {
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(remaining.substring(2, endIndex))
                    }
                    remaining = remaining.substring(endIndex + 2)
                } else {
                    append(remaining[0])
                    remaining = remaining.substring(1)
                }
            }

            // Italic *text*
            remaining.startsWith("*") && !remaining.startsWith("**") -> {
                val endIndex = remaining.indexOf("*", 1)
                if (endIndex != -1) {
                    withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
                        append(remaining.substring(1, endIndex))
                    }
                    remaining = remaining.substring(endIndex + 1)
                } else {
                    append(remaining[0])
                    remaining = remaining.substring(1)
                }
            }

            // Code `text`
            remaining.startsWith("`") -> {
                val endIndex = remaining.indexOf("`", 1)
                if (endIndex != -1) {
                    withStyle(
                        SpanStyle(
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                            background = codeBackgroundColor
                        )
                    ) {
                        append(remaining.substring(1, endIndex))
                    }
                    remaining = remaining.substring(endIndex + 1)
                } else {
                    append(remaining[0])
                    remaining = remaining.substring(1)
                }
            }

            // Regular character
            else -> {
                append(remaining[0])
                remaining = remaining.substring(1)
            }
        }
    }
}
