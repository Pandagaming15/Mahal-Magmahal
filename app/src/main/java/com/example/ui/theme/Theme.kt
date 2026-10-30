package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val MelancholicGameColorScheme = darkColorScheme(
    primary = CapizAmber,
    onPrimary = TwilightDark,
    primaryContainer = TwilightSurfaceLight,
    onPrimaryContainer = CapizGlow,
    secondary = RainBlue,
    onSecondary = SampaguitaWhite,
    secondaryContainer = TwilightSurface,
    onSecondaryContainer = RainCyan,
    tertiary = HeartRed,
    onTertiary = SampaguitaWhite,
    background = TwilightDark,
    onBackground = SampaguitaWhite,
    surface = TwilightDeep,
    onSurface = SampaguitaWhite,
    surfaceVariant = TwilightSurface,
    onSurfaceVariant = MutedText,
    outline = BorderGold
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MelancholicGameColorScheme,
        typography = AppTypography,
        content = content
    )
}
