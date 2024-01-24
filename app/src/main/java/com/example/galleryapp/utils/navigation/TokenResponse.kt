package com.example.galleryapp.utils.navigation

data class TokenResponse(
    val tokenDto: TokenDto,
    val user: User,
    val isSuccessful: Boolean,
    val responseMessage: String,
    val responseCode: String
)

data class TokenDto(val expiresIn: String, val token: String)
data class User(val id: String, val fullName: String, val email: String)