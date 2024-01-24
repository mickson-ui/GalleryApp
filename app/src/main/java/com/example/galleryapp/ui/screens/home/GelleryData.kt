package com.example.galleryapp.ui.screens.home

import com.example.galleryapp.R

data class Art(
    val id: String,
    val name: String,
    val price: String,
    val image: String,
    val description: String,
    val createdAt: String
)

data class ArtsResponse(
    val isSuccessful: Boolean,
    val responseCode: String,
    val responseMessage: String,
    val arts: List<Art> // Assuming this is the structure of your response
)