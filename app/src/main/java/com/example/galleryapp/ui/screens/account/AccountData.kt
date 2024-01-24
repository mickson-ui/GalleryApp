package com.example.galleryapp.ui.screens.account

import com.example.galleryapp.ui.screens.home.Art
import com.example.galleryapp.ui.screens.register.User

data class UserResponse(
    val user: User,
    val arts: List<Art>,
    val isSuccessful: Boolean,
    val responseMessage: String,
    val responseCode: String
)