package com.example.photosearchapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashSearchResults(
    val total: Int, @SerialName("total_pages") val totalPages: Int, val results: List<UnsplashPhoto>
)