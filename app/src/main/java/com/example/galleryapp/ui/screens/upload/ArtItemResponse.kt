package com.example.galleryapp.ui.screens.upload

data class ArtItemRequest(
    val name: String,
    val price: String,
    val image: String,
    val description: String,
    val userId: String
)

data class ArtUploadApiResponse(
    val isSuccessful: Boolean,
    val responseMessage: String,
    val responseCode: String
)
