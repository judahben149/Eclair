package com.judahben149.eclair.presentation.screens.train

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.judahben149.eclair.navigation.TrainComponent

@Composable
fun TrainScreen(component: TrainComponent) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Train",
            style = MaterialTheme.typography.headlineLarge
        )
    }
}
