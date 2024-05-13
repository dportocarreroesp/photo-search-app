package com.example.photosearchapp.presentation.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Button
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.Icon
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.example.photosearchapp.R

@Composable
fun SearchBar(
    searchQuery: String,
    setSearchQuery: (String) -> Unit,
    photosViewModel: PhotosViewModel,
    modifier: Modifier = Modifier
) {
    var showSearchInput by rememberSaveable { mutableStateOf(false) }
    var focusingDone by remember { mutableStateOf(false) }
    val focusRequester by remember { mutableStateOf(FocusRequester()) }

    Row(modifier) {
        if (!showSearchInput) {
            Button(
                onClick = {
                    showSearchInput = true
                    setSearchQuery("")
                },
                shape = ButtonDefaults.shape(shape = CircleShape),
                modifier = modifier.size(40.dp),
                contentPadding = PaddingValues(1.dp),
                colors = ButtonDefaults.colors(MaterialTheme.colorScheme.secondary)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    modifier = Modifier.size(25.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        } else {
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
                focusingDone = true
            }

            BasicTextField(value = searchQuery,
                onValueChange = { setSearchQuery(it) },
                textStyle = MaterialTheme.typography.displaySmall,
                singleLine = true,
                decorationBox = {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(vertical = 8.dp)
                            .padding(start = 12.dp),
                    ) {
                        it()
                        if (searchQuery.isEmpty()) {
                            Text(
                                modifier = Modifier.graphicsLayer { alpha = 0.6f },
                                text = stringResource(R.string.search),
                                style = MaterialTheme.typography.displaySmall
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false, imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(onSearch = {
                    if (searchQuery.isNotEmpty()) {
                        photosViewModel.clearSearch()
                        photosViewModel.searchPhotos(searchQuery)
                    }
                    showSearchInput = false
                    focusingDone = false
                }),
                modifier = Modifier
                    .width(400.dp)
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        if (focusingDone && !it.isFocused) {
                            showSearchInput = false
                            focusingDone = false
                        }
                    })

        }
    }
}