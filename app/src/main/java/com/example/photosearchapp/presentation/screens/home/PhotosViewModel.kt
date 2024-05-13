package com.example.photosearchapp.presentation.screens.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.photosearchapp.PhotosSearchApplication
import com.example.photosearchapp.data.UnsplashPhotosRepository
import com.example.photosearchapp.model.UnsplashPhoto
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// Note: If you implement PhotoQueryState interface without a sealed keyword, it requires you to add a Success, Error, Loading and else branch. Since there is no fourth option (else), you use a sealed interface to tell the compiler that there are only three options (thus making the conditionals exhaustive).
sealed interface PhotoQueryState {
    object Success : PhotoQueryState
    object Error : PhotoQueryState
    object Loading : PhotoQueryState
}

class PhotosViewModel(private val unsplashPhotosRepository: UnsplashPhotosRepository) :
    ViewModel() {
    var photoQueryState: PhotoQueryState by mutableStateOf(PhotoQueryState.Loading)
        private set
    var currentPhotoData: List<UnsplashPhoto> by mutableStateOf(emptyList())
    var currentSearch: String? by mutableStateOf(null)
    var currentPhotoPage: Int? by mutableStateOf(null)
    var totalPhotoPages: Int? by mutableStateOf(null)

    init {
        getRandomPhotos()
    }

    fun getRandomPhotos() {
        viewModelScope.launch {
            try {
                val listResult = unsplashPhotosRepository.getRandomPhotos(20)
                currentPhotoData += listResult
                photoQueryState = PhotoQueryState.Success

            } catch (e: IOException) {
                Log.e("ERROR", e.message.toString());
                photoQueryState = PhotoQueryState.Error
            } catch (e: HttpException) {
                Log.e("ERROR", e.message.toString());
                photoQueryState = PhotoQueryState.Error
            }
        }
    }

    fun searchPhotos(query: String) {
        viewModelScope.launch {
            try {
                val data = unsplashPhotosRepository.searchPhotos(
                    query,
                    page = if (currentPhotoPage != null) currentPhotoPage!! + 1 else null,
                    totalPhotoPages
                )

                if (currentPhotoPage != null) {
                    currentPhotoPage = currentPhotoPage!! + 1
                    currentPhotoData += data.results
                } else {
                    currentPhotoData = data.results
                    currentPhotoPage = 1
                }

                totalPhotoPages = data.totalPages
                photoQueryState = PhotoQueryState.Success
            } catch (e: IOException) {
                Log.e("ERROR", e.message.toString());
                photoQueryState = PhotoQueryState.Error
            } catch (e: HttpException) {
                Log.e("ERROR", e.message.toString());
                photoQueryState = PhotoQueryState.Error
            } finally {
                currentSearch = query
            }
        }
    }

    fun clearSearch() {
        currentPhotoData = emptyList()
        currentSearch = null
        currentPhotoPage = null
        totalPhotoPages = null
    }

    fun fetchMoreData() {
        viewModelScope.launch {
            try {
                if (currentSearch != null) {
                    searchPhotos(currentSearch!!)
                } else {
                    getRandomPhotos()
                }
            } catch (e: IOException) {
                Log.e("ERROR", e.message.toString());
            }
        }
    }

    // Factory because viewModels don't allow to pass values on initialization
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as PhotosSearchApplication
                val unsplashPhotosRepository = application.container.unsplashPhotosRepository
                PhotosViewModel(unsplashPhotosRepository = unsplashPhotosRepository)
            }
        }
    }
}