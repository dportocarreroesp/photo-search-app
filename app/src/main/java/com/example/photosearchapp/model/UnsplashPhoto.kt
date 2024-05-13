package com.example.photosearchapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashUser(
    val id: String,
    val username: String,
)

@Serializable
data class UnsplashUrls(
    val raw: String?,
    val full: String?,
    val thumb: String?,
)

@Serializable
data class UnsplashPhoto(
    val id: String,
    @SerialName("created_at") val createdAt: String,
    val description: String?,
    @SerialName("alt_description") val altDescription: String?,
    val user: UnsplashUser,
    val urls: UnsplashUrls,
)