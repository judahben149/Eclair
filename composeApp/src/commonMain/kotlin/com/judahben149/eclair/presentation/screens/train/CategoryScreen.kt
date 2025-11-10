package com.judahben149.eclair.presentation.screens.train

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.judahben149.eclair.navigation.CategoryType
import com.judahben149.eclair.navigation.TrainComponent
import com.judahben149.eclair.presentation.screens.studio.components.PlatformTopBar

@Composable
fun CategoryScreen(
    component: TrainComponent,
    categoryType: CategoryType
) {
    val title = when (categoryType) {
        CategoryType.STAGE_BASICS -> "Stage Basics"
        CategoryType.CONCERT_LIGHTING -> "Concert Lighting"
        CategoryType.THEATRE -> "Theatre"
        CategoryType.EQUIPMENT -> "Equipment"
        CategoryType.COLOUR_THEORY -> "Colour Theory"
        CategoryType.DMX -> "DMX"
    }

    Scaffold(
        topBar = {
            PlatformTopBar(
                title = title,
                onBackClick = component::onBackClicked
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Content coming soon...",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
