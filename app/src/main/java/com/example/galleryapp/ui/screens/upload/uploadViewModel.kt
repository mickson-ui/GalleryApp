package com.example.galleryapp.ui.screens.upload

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class UploadViewModel : ViewModel() {

    private val _itemName = mutableStateOf("")
    val itemName: State<String> = _itemName

    private val _itemPrice = mutableStateOf("")
    val itemPrice: State<String> = _itemPrice

    private val _itemDescription = mutableStateOf("")
    val itemDescription: State<String> = _itemDescription

    fun onItemNameChanged(name: String) {
        _itemName.value = name
    }

    fun onItemPriceChanged(price: String) {
        _itemPrice.value = price
    }

    fun onItemDescriptionChanged(description: String) {
        _itemDescription.value = description
    }

    fun uploadItemToDatabase() {
        // Add logic to save the item data to the database
        // You can use a repository to handle database operations
    }
}
