package com.example.galleryapp.ui.screens.itemDetails

import com.example.galleryapp.R
import com.example.galleryapp.ui.screens.home.Art


data class ItemDetailsResponse(
    val isSuccessful: Boolean,
    val responseCode: String,
    val responseMessage: String,
    val art: Art?
)

data class DeleteResponse(
    val isSuccessful: Boolean,
    val responseCode: String,
    val responseMessage: String,
)
