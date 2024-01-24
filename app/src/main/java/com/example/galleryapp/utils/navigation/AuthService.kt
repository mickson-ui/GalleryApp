package com.example.galleryapp.utils.navigation

class AuthService {
    private val users = mapOf(
        "user@example.com" to "password",
        "user1@example.com" to "password1",
        "user2@example.com" to "password2",
    )

    fun loginUser(email: String, password: String): Boolean{
        val storedPassword = users[email]
        return storedPassword != null && storedPassword == password
    }

    fun isUserAuthenticated(token: String): Boolean{
        return token.isNotEmpty()
    }
}