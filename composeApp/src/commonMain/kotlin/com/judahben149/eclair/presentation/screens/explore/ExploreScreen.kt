package com.judahben149.eclair.presentation.screens.explore

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.judahben149.eclair.navigation.ExploreComponent

@Composable
fun ExploreScreen(component: ExploreComponent) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Explore",
            style = MaterialTheme.typography.headlineLarge
        )
    }
}
