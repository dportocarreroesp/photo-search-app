package com.example.photosearchapp.ui.theme

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.darkColorScheme
import androidx.tv.material3.lightColorScheme

@Composable
fun PhotoSearchAppTheme(
    isInDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (isInDarkTheme) {
        darkColorScheme(
            primary = White80,
            secondary = Turquoise80,
            tertiary = Turquoise80,
            surface = Black80,
        )
    } else {
        lightColorScheme(
            primary = White80,
            secondary = Turquoise80,
            tertiary = Turquoise80,
            surface = Black80
        )
    }
    MaterialTheme(
        colorScheme = colorScheme,
        shapes = Shapes,
        typography = Typography,
        content = content
    )
}