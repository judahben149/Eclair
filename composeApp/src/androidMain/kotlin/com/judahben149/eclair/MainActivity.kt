package com.judahben149.eclair

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.cactus.CactusContextInitializer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        CactusContextInitializer.initialize(this)

        setContent {
            EclairApp()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    EclairApp()
}