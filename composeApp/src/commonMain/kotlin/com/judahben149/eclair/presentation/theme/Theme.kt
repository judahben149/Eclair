package com.judahben149.eclair.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val TealBlue = Color(0xFF4A9B9E)
val TealBlueDark = Color(0xFF367A7D)
val TealBlueLight = Color(0xFF6BB5B8)

private val LightColorScheme = lightColorScheme(
    primary = TealBlue,
    onPrimary = Color.White,
    primaryContainer = TealBlueLight,
    onPrimaryContainer = Color(0xFF003739),
    secondary = Color(0xFF4A6363),
    onSecondary = Color.White,
    surface = Color(0xFFFBFDFD),
    onSurface = Color(0xFF191C1C),
    surfaceVariant = Color(0xFFDAE5E5),
    onSurfaceVariant = Color(0xFF3F4949)
)

private val DarkColorScheme = darkColorScheme(
    primary = TealBlueLight,
    onPrimary = Color(0xFF003739),
    primaryContainer = TealBlueDark,
    onPrimaryContainer = TealBlueLight,
    secondary = Color(0xFFB0CCCC),
    onSecondary = Color(0xFF1B3535),
    surface = Color(0xFF191C1C),
    onSurface = Color(0xFFE0E3E3),
    surfaceVariant = Color(0xFF3F4949),
    onSurfaceVariant = Color(0xFFBFC9C9)
)

@Composable
fun EclairTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val typography = EclairTypography()

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}
