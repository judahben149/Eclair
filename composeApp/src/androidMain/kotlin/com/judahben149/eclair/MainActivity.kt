package com.judahben149.eclair

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.defaultComponentContext
import com.judahben149.eclair.navigation.DefaultRootComponent
//import com.cactus.CactusContextInitializer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

//        CactusContextInitializer.initialize(this)

        val rootComponent = DefaultRootComponent(defaultComponentContext())

        setContent {
            EclairApp(rootComponent)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    // Note: Preview won't work without a proper ComponentContext
    // For preview, you'd need to create a fake component or mock
}