package com.example.galleryapp.ui.screens.home

import com.example.galleryapp.R

data class GalleryItem(
    val id: Int,
    val itemName: String,
    val price: String,
    val imageUrl: Int,
    val isFavorite: Boolean,
    val description: String
)
val dummyData = listOf(
    GalleryItem(1, "Item 1", "$20", R.drawable.img1, false, "This is a sample item description. Lorem ipsum dolor sit amet, consectetur adipiscing elit.",),
    GalleryItem(2, "Item 2", "$30", R.drawable.img2, true,"This is a sample item description. Lorem ipsum dolor sit amet, consectetur adipiscing elit.",),
    GalleryItem(3, "Item 2", "$30", R.drawable.img1, true,"This is a sample item description. Lorem ipsum dolor sit amet, consectetur adipiscing elit.",),
    GalleryItem(4, "Item 2", "$30", R.drawable.img2, true,"This is a sample item description. Lorem ipsum dolor sit amet, consectetur adipiscing elit.",),
    // Add more dummy data
)