package com.example.photosearchapp.presentation.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.grid.TvGridCells
import androidx.tv.foundation.lazy.grid.TvLazyGridScope
import androidx.tv.foundation.lazy.grid.TvLazyGridState
import androidx.tv.foundation.lazy.grid.TvLazyVerticalGrid
import androidx.tv.foundation.lazy.grid.rememberTvLazyGridState

@Composable
fun HomeLazyGrid(
    gridState: TvLazyGridState = rememberTvLazyGridState(),
    modifier: Modifier = Modifier,
    content: TvLazyGridScope.() -> Unit
) {
    TvLazyVerticalGrid(
        columns = TvGridCells.Adaptive(232.dp),
        state = gridState,
        modifier = modifier,
    ) {
        content()
    }
}