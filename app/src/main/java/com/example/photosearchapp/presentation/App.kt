package com.example.photosearchapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.tv.material3.MaterialTheme
import com.example.photosearchapp.presentation.screens.home.Home
import com.example.photosearchapp.presentation.screens.home.PhotosViewModel

@Composable
fun App() {
    val photosViewModel: PhotosViewModel = viewModel(factory = PhotosViewModel.Factory)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
    ) {
        Home(
            photosViewModel,
            modifier = Modifier.padding(start = 20.dp, top = 20.dp, end = 20.dp)
        )
    }
}

