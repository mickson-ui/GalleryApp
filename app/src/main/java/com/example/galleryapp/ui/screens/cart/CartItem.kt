package com.example.galleryapp.ui.screens.cart

import com.example.galleryapp.R

data class CartItem(
    val id: Int,
    val itemName: String,
    val price: String,
    val imageUrl: Int,
    val isFavorite: Boolean
)

val dummyCartItems = listOf(
    CartItem(1, "Item 1", "$20.0", R.drawable.img1, false),
    CartItem(2, "Item 2", "$30.0", R.drawable.img2, false),
    CartItem(3, "Item 3", "$25.0", R.drawable.img1, false)
    // Add more items as needed
)