package com.example.photosearchapp.presentation.screens.home

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.dp
import com.example.photosearchapp.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.tv.foundation.lazy.grid.TvGridCells
import androidx.tv.foundation.lazy.grid.TvLazyGridScope
import androidx.tv.foundation.lazy.grid.TvLazyVerticalGrid
import androidx.tv.material3.MaterialTheme

@Composable
fun LoadingView(modifier: Modifier = Modifier) {
    HomeLazyGrid {
        items(count = 9) {
            BoxSkeleton(
                shape = MaterialTheme.shapes.large,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .aspectRatio(1.5f)
            )
        }
    }
}

@Composable
fun BoxSkeleton(shape: Shape, modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "transition")

    val translateAnimation by transition.animateFloat(
        initialValue = 0f, targetValue = 400f, animationSpec = infiniteRepeatable(
            tween(durationMillis = 2500, easing = LinearOutSlowInEasing), RepeatMode.Restart
        ), label = "translate"
    )

    val shimmerColors = listOf(
        Color.Gray.copy(alpha = 0.9f),
        Color.Gray.copy(alpha = 0.4f),
    )
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnimation, translateAnimation),
        end = Offset(translateAnimation + 200f, translateAnimation + 100f),
        tileMode = TileMode.Mirror,
    )

    Box(
        modifier = modifier.background(brush, shape)
    )
}