package com.example.photosearchapp.presentation.screens.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.grid.items
import androidx.tv.foundation.lazy.grid.rememberTvLazyGridState
import androidx.tv.material3.Border
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.CompactCard
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.photosearchapp.R
import com.example.photosearchapp.model.UnsplashPhoto

private const val OFFSET = 3

@Composable
fun PhotosGridView(
    photos: List<UnsplashPhoto>, fetchMoreData: () -> Unit, modifier: Modifier = Modifier
) {
    val gridState = rememberTvLazyGridState()

    val reachedBottom: Boolean by remember {
        derivedStateOf {
            val lastVisibleItem = gridState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != null && lastVisibleItem?.index != 0 && (lastVisibleItem?.index
                ?: 0) >= gridState.layoutInfo.totalItemsCount - OFFSET
        }
    }

    LaunchedEffect(reachedBottom) {
        if (reachedBottom) {
            fetchMoreData()
        }
    }

    HomeLazyGrid(gridState) {
        items(items = photos, key = { photo -> photo.id }) { photo ->
            UnsplashPhotoCard(
                photo, modifier = modifier
                    .padding(8.dp)
                    .aspectRatio(1.5f)
            )
        }
    }
}

@Composable
fun UnsplashPhotoCard(photo: UnsplashPhoto, modifier: Modifier = Modifier) {
    CompactCard(
        onClick = { },
        border = CardDefaults.border(
            focusedBorder = Border(
                border = BorderStroke(width = 3.dp, color = MaterialTheme.colorScheme.primary),
                shape = MaterialTheme.shapes.large,
            )
        ),
        scale = CardDefaults.scale(
            focusedScale = 1.05f,
        ),
        shape = CardDefaults.shape(
            shape = MaterialTheme.shapes.large
        ),
        image = {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(photo.urls.full ?: photo.urls.raw).crossfade(true).build(),
                error = painterResource(R.drawable.ic_connection_error),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = "Photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
        },
        title = {
            Text(
                text = photo.description ?: photo.altDescription ?: photo.id,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(4.dp)
            )
        },
        subtitle = {
            Text(
                text = "${photo.user.username} / ${photo.createdAt}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(4.dp)
            )
        },
        modifier = modifier,
    )
}