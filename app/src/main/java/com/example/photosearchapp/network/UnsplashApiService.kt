package com.example.photosearchapp.network

import com.example.photosearchapp.model.UnsplashPhoto
import com.example.photosearchapp.model.UnsplashSearchResults
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApiService {
    // TODO: use .env variables
    @Headers("Authorization: Client-ID YOUR-API-KEY-HERE")
    @GET("photos/random")
    suspend fun getRandomPhotos(
        @Query("count") count: Int,
    ): List<UnsplashPhoto>

    @Headers("Authorization: Client-ID YOUR-API-KEY-HERE")
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String, @Query("page") page: Int?
    ): UnsplashSearchResults
}
