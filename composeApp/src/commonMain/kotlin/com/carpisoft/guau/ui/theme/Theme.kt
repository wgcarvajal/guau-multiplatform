package com.carpisoft.guau.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Green100,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    primaryContainer = Green100,
    onPrimaryContainer = Green100,
    background = Background,
    onSurface = OnSurface,
    outline = Outline,
    onSurfaceVariant = OnSurfaceVariant,
    surface = SurfaceLight
)

private val DarkColorScheme = darkColorScheme(
    surface = SurfaceDark
)


@Composable
fun GuauTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = if (isSystemInDarkTheme()) {
        DarkColorScheme
    } else {
        LightColorScheme
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}