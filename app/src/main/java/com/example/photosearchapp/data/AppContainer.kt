package com.example.photosearchapp.data

import com.example.photosearchapp.network.UnsplashApiService
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

interface AppContainer {
    val unsplashPhotosRepository: UnsplashPhotosRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://api.unsplash.com/"

    private val jsonSettings = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(jsonSettings.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val retrofitService: UnsplashApiService by lazy {
        retrofit.create(UnsplashApiService::class.java)
    }

    override val unsplashPhotosRepository: UnsplashPhotosRepository by lazy {
        NetworkUnsplashPhotosRepository(retrofitService)
    }
}