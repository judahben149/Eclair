package com.judahben149.eclair.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import eclair.composeapp.generated.resources.Res
import eclair.composeapp.generated.resources.sohne_bold
import eclair.composeapp.generated.resources.sohne_bold_italic
import eclair.composeapp.generated.resources.sohne_extralight
import eclair.composeapp.generated.resources.sohne_extralight_italic
import eclair.composeapp.generated.resources.sohne_light
import eclair.composeapp.generated.resources.sohne_light_italic
import eclair.composeapp.generated.resources.sohne_medium
import eclair.composeapp.generated.resources.sohne_medium_italic
import eclair.composeapp.generated.resources.sohne_regular
import eclair.composeapp.generated.resources.sohne_regular_italic
import eclair.composeapp.generated.resources.sohne_semibold
import eclair.composeapp.generated.resources.sohne_semibold_italic
import org.jetbrains.compose.resources.Font

@Composable
fun SohneFontFamily() = FontFamily(
    Font(Res.font.sohne_extralight, FontWeight.ExtraLight, FontStyle.Normal),
    Font(Res.font.sohne_extralight_italic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(Res.font.sohne_light, FontWeight.Light, FontStyle.Normal),
    Font(Res.font.sohne_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(Res.font.sohne_regular, FontWeight.Normal, FontStyle.Normal),
    Font(Res.font.sohne_regular_italic, FontWeight.Normal, FontStyle.Italic),
    Font(Res.font.sohne_medium, FontWeight.Medium, FontStyle.Normal),
    Font(Res.font.sohne_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(Res.font.sohne_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(Res.font.sohne_semibold_italic, FontWeight.SemiBold, FontStyle.Italic),
    Font(Res.font.sohne_bold, FontWeight.Bold, FontStyle.Normal),
    Font(Res.font.sohne_bold_italic, FontWeight.Bold, FontStyle.Italic),
)

@Composable
fun EclairTypography(): Typography {
    val sohneFamily = SohneFontFamily()

    return Typography(
        displayLarge = TextStyle(
            fontFamily = sohneFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 57.sp,
            lineHeight = 64.sp,
            letterSpacing = (-0.25).sp,
        ),
        displayMedium = TextStyle(
            fontFamily = sohneFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 45.sp,
            lineHeight = 52.sp,
            letterSpacing = 0.sp,
        ),
        displaySmall = TextStyle(
            fontFamily = sohneFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 36.sp,
            lineHeight = 44.sp,
            letterSpacing = 0.sp,
        ),
        headlineLarge = TextStyle(
            fontFamily = sohneFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 32.sp,
            lineHeight = 40.sp,
            letterSpacing = 0.sp,
        ),
        headlineMedium = TextStyle(
            fontFamily = sohneFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 28.sp,
            lineHeight = 36.sp,
            letterSpacing = 0.sp,
        ),
        headlineSmall = TextStyle(
            fontFamily = sohneFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp,
            lineHeight = 32.sp,
            letterSpacing = 0.sp,
        ),
        titleLarge = TextStyle(
            fontFamily = sohneFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp,
        ),
        titleMedium = TextStyle(
            fontFamily = sohneFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.15.sp,
        ),
        titleSmall = TextStyle(
            fontFamily = sohneFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp,
        ),
        bodyLarge = TextStyle(
            fontFamily = sohneFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp,
        ),
        bodyMedium = TextStyle(
            fontFamily = sohneFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.25.sp,
        ),
        bodySmall = TextStyle(
            fontFamily = sohneFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.4.sp,
        ),
        labelLarge = TextStyle(
            fontFamily = sohneFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp,
        ),
        labelMedium = TextStyle(
            fontFamily = sohneFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp,
        ),
        labelSmall = TextStyle(
            fontFamily = sohneFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp,
        ),
    )
}
