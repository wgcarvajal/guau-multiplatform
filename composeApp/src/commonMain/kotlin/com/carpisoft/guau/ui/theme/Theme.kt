package com.carpisoft.guau.ui.theme

import androidx.compose.material3.MaterialTheme
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
    onSurfaceVariant = OnSurfaceVariant
)


@Composable
fun GuauTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}