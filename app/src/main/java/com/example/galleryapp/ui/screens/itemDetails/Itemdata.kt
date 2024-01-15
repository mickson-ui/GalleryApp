package com.example.galleryapp.ui.screens.itemDetails

import com.example.galleryapp.R

data class ItemDetails(
    val id: Int,
    val itemName: String,
    val price: String,
    val imageUrl: Int,
    val description: String,
    val isFavorite: Boolean
)

val dummyItemDetails = ItemDetails(
    id = 1,
    itemName = "Happy New Year",
    price = "$29.99",
    description = "This is a sample item description. Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
    imageUrl = R.drawable.img1,
    isFavorite = false
)