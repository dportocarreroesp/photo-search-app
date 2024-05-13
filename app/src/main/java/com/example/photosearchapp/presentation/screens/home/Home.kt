package com.example.photosearchapp.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.example.photosearchapp.R

@Composable
fun Home(photosViewModel: PhotosViewModel, modifier: Modifier = Modifier) {
    var searchQuery by rememberSaveable { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize()) {

        val photoQueryState = photosViewModel.photoQueryState
        HomeHeader(searchQuery, setSearchQuery = { searchQuery = it }, photosViewModel)

        Text(
            text = if (photosViewModel.currentSearch == null) stringResource(R.string.trending_now)
            else if (photosViewModel.currentPhotoData.isNotEmpty()) stringResource(
                R.string.search_results_for, photosViewModel.currentSearch!!
            ) else stringResource(
                R.string.no_search_results_for, photosViewModel.currentSearch!!
            ),
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 2.dp)
        )

        // TODO: add a test to each view
        when (photoQueryState) {
            is PhotoQueryState.Loading -> LoadingView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 0.dp)
            )

            is PhotoQueryState.Error -> ErrorView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 0.dp)
            )

            is PhotoQueryState.Success -> PhotosGridView(
                photos = photosViewModel.currentPhotoData,
                fetchMoreData = { photosViewModel.fetchMoreData() },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 0.dp)
            )
        }

    }


}

@Composable
fun HomeHeader(
    searchQuery: String, setSearchQuery: (String) -> Unit, photosViewModel: PhotosViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SearchBar(
            searchQuery, setSearchQuery, photosViewModel, modifier = Modifier.padding(top = 12.dp)
        )
        Box(
            contentAlignment = Alignment.TopEnd,
        ) {
            Text(
                text = "PHOTO SEARCH",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
            )
        }

    }
}
