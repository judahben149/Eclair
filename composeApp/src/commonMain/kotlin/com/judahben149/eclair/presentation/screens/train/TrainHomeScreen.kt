package com.judahben149.eclair.presentation.screens.train

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.judahben149.eclair.navigation.TrainComponent
import com.judahben149.eclair.presentation.screens.train.models.LearningCategory
import com.judahben149.eclair.presentation.screens.train.models.PatternType
import com.judahben149.eclair.presentation.screens.train.models.getAllCategories
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.absoluteValue

@Composable
fun TrainHomeScreen(component: TrainComponent) {
    val viewModel: TrainHomeViewModel = koinViewModel()
    val conceptListState by viewModel.conceptListState.collectAsState()

    // Get concepts list for carousel
    val concepts = when (val state = conceptListState) {
        is ConceptListState.Success -> state.concepts
        else -> emptyList()
    }

    val pagerState = rememberPagerState(pageCount = { concepts.size })

    val currentConcept by remember {
        derivedStateOf {
            concepts.getOrNull(pagerState.currentPage)
        }
    }

    // Assign pattern based on concept index
    val currentPattern = currentConcept?.let { concept ->
        getPatternForIndex(concepts.indexOf(concept))
    }

    val currentColorPair = currentPattern?.let { pattern ->
        getColorsForPattern(pattern)
    }

    val backgroundColor by animateColorAsState(
        targetValue = currentColorPair?.first?.copy(alpha = 0.05f) ?: Color.Transparent,
        animationSpec = tween(durationMillis = 600),
        label = "background_color"
    )

    Scaffold(
        containerColor = backgroundColor,
        floatingActionButton = {
            FloatingActionButton(
                onClick = component::onCustomLearningPathClicked,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create Custom Learning Path"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Header
            Text(
                text = "Train",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                ),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Dynamic Concept Carousel
            when (val state = conceptListState) {
                is ConceptListState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is ConceptListState.Success -> {
                    if (state.concepts.isEmpty()) {
                        // Empty state
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No concepts available",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        HorizontalPager(
                            state = pagerState,
                            pageSize = PageSize.Fixed(280.dp),
                            contentPadding = PaddingValues(horizontal = 48.dp),
                            pageSpacing = 16.dp,
                            modifier = Modifier.fillMaxWidth()
                        ) { page ->
                            val concept = state.concepts[page]
                            val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                            val scaleFactor = 1f - (pageOffset.absoluteValue * 0.1f).coerceIn(0f, 0.1f)
                            val alphaFactor = 1f - (pageOffset.absoluteValue * 0.3f).coerceIn(0f, 0.3f)

                            DynamicConceptCard(
                                concept = concept,
                                pattern = getPatternForIndex(page),
                                colors = getColorsForPattern(getPatternForIndex(page)),
                                onClick = { component.onConceptClicked(concept.id) },
                                modifier = Modifier
                                    .scale(scaleFactor)
                                    .alpha(alphaFactor)
                                    .graphicsLayer {
                                        val rotation = pageOffset * 10f
                                        rotationY = rotation.coerceIn(-10f, 10f)
                                    }
                            )
                        }
                    }
                }
                is ConceptListState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .padding(horizontal = 48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "Failed to load concepts",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.error
                            )
                            TextButton(onClick = { viewModel.retry() }) {
                                Text("Retry")
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Equipment Identification Card
            EquipmentIdentificationCard(
                onClick = component::onIdentifyEquipmentClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun CategoryCard(
    category: LearningCategory,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val shimmerAlpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shimmer_alpha"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(category.colorStart, category.colorEnd)
                    )
                )
        ) {
            // Pattern background
            PatternCanvas(
                pattern = category.pattern,
                color = Color.White.copy(alpha = 0.1f),
                modifier = Modifier.fillMaxSize()
            )

            // Shimmer effect
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(shimmerAlpha * 0.3f)
            ) {
                val shimmerWidth = size.width * 0.3f
                val shimmerX = (size.width + shimmerWidth) * shimmerAlpha - shimmerWidth

                drawRect(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.White.copy(alpha = 0.3f),
                            Color.Transparent
                        ),
                        startX = shimmerX,
                        endX = shimmerX + shimmerWidth
                    )
                )
            }

            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = category.title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = category.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}

@Composable
private fun PatternCanvas(
    pattern: PatternType,
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        when (pattern) {
            PatternType.CIRCLES -> {
                for (i in 0..5) {
                    for (j in 0..8) {
                        drawCircle(
                            color = color,
                            radius = 30f,
                            center = Offset(
                                x = i * size.width / 5f,
                                y = j * size.height / 8f
                            )
                        )
                    }
                }
            }
            PatternType.WAVES -> {
                for (i in 0..20) {
                    val y = size.height * i / 20f
                    drawLine(
                        color = color,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = 2f
                    )
                }
            }
            PatternType.CURTAINS -> {
                for (i in 0..10) {
                    val x = size.width * i / 10f
                    drawLine(
                        color = color,
                        start = Offset(x, 0f),
                        end = Offset(x, size.height),
                        strokeWidth = 3f
                    )
                }
            }
            PatternType.GEARS -> {
                for (i in 0..4) {
                    for (j in 0..6) {
                        drawCircle(
                            color = color,
                            radius = 25f,
                            center = Offset(
                                x = i * size.width / 4f,
                                y = j * size.height / 6f
                            )
                        )
                        drawCircle(
                            color = color,
                            radius = 15f,
                            center = Offset(
                                x = i * size.width / 4f,
                                y = j * size.height / 6f
                            )
                        )
                    }
                }
            }
            PatternType.PRISM -> {
                for (i in 0..8) {
                    val startX = i * size.width / 8f
                    drawLine(
                        color = color,
                        start = Offset(startX, 0f),
                        end = Offset(startX + size.width / 16f, size.height),
                        strokeWidth = 2f
                    )
                }
            }
            PatternType.CIRCUITS -> {
                for (i in 0..6) {
                    for (j in 0..10) {
                        val x = i * size.width / 6f
                        val y = j * size.height / 10f
                        drawLine(
                            color = color,
                            start = Offset(x, y),
                            end = Offset(x + size.width / 12f, y),
                            strokeWidth = 2f
                        )
                        drawCircle(
                            color = color,
                            radius = 5f,
                            center = Offset(x, y)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EquipmentIdentificationCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(180.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Camera,
                    contentDescription = "Camera",
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Identify Equipment",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

// Helper functions for dynamic pattern assignment
private fun getPatternForIndex(index: Int): PatternType {
    val patterns = PatternType.values()
    return patterns[index % patterns.size]
}

private fun getColorsForPattern(pattern: PatternType): Pair<Color, Color> {
    return when (pattern) {
        PatternType.CIRCLES -> Pair(Color(0xFF6B46C1), Color(0xFF9333EA))
        PatternType.WAVES -> Pair(Color(0xFF2563EB), Color(0xFF0EA5E9))
        PatternType.CURTAINS -> Pair(Color(0xFFDC2626), Color(0xFFF97316))
        PatternType.GEARS -> Pair(Color(0xFF059669), Color(0xFF10B981))
        PatternType.PRISM -> Pair(Color(0xFFD97706), Color(0xFFF59E0B))
        PatternType.CIRCUITS -> Pair(Color(0xFF7C3AED), Color(0xFFA855F7))
    }
}

@Composable
private fun DynamicConceptCard(
    concept: com.judahben149.eclair.data.remote.dto.ConceptListItem,
    pattern: PatternType,
    colors: Pair<Color, Color>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val shimmerAlpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shimmer_alpha"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(colors.first, colors.second)
                    )
                )
        ) {
            // Pattern background
            PatternCanvas(
                pattern = pattern,
                color = Color.White.copy(alpha = 0.1f),
                modifier = Modifier.fillMaxSize()
            )

            // Shimmer effect
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(shimmerAlpha * 0.3f)
            ) {
                val shimmerWidth = size.width * 0.3f
                val shimmerX = (size.width + shimmerWidth) * shimmerAlpha - shimmerWidth

                drawRect(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.White.copy(alpha = 0.3f),
                            Color.Transparent
                        ),
                        startX = shimmerX,
                        endX = shimmerX + shimmerWidth
                    )
                )
            }

            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = concept.title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                concept.description?.let { description ->
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.9f),
                        maxLines = 3
                    )
                }
            }
        }
    }
}
