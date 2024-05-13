package com.example.photosearchapp.data

import com.example.photosearchapp.model.UnsplashPhoto
import com.example.photosearchapp.model.UnsplashSearchResults
import com.example.photosearchapp.network.UnsplashApiService

interface UnsplashPhotosRepository {
    suspend fun getRandomPhotos(count: Int): List<UnsplashPhoto>
    suspend fun searchPhotos(query: String, page: Int?, totalPages: Int?): UnsplashSearchResults
}

class NetworkUnsplashPhotosRepository(
    private val unsplashApiService: UnsplashApiService
) : UnsplashPhotosRepository {
    override suspend fun getRandomPhotos(count: Int): List<UnsplashPhoto> =
        unsplashApiService.getRandomPhotos(count)

    override suspend fun searchPhotos(
        query: String, page: Int?, totalPages: Int?
    ): UnsplashSearchResults {
        if (query.isEmpty()) {
            return UnsplashSearchResults(results = emptyList(), total = 0, totalPages = 0)
        }

        if (page != null && totalPages != null && page >= totalPages) {
            return UnsplashSearchResults(results = emptyList(), total = 0, totalPages = 0)
        }

        return unsplashApiService.searchPhotos(query, page)
    }
}