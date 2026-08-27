package com.example.trail.shared.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

val DarkColorScheme = darkColorScheme(
    primary = TrailColors.Purple80,
    secondary = TrailColors.PurpleGrey80,
    tertiary = TrailColors.Pink80
)

val LightColorScheme = lightColorScheme(
    primary = TrailColors.Primary,
    tertiary = TrailColors.Pink40,
    background = TrailColors.Background,
    secondary = TrailColors.AuthorNameStyling
)

@Composable
fun TrailTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = TrailTypography,
        content = content
    )
}
