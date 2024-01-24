package com.example.galleryapp.ui.screens.upload

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.galleryapp.network.GalleryApi
import com.example.galleryapp.network.GalleryApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UploadViewModel : ViewModel() {
    // State variables to observe in the UI
    private val _itemName = mutableStateOf("")
    val itemName get() = _itemName

    private val _itemPrice = mutableStateOf("")
    val itemPrice get() = _itemPrice

    private val _itemDescription = mutableStateOf("")
    val itemDescription get() = _itemDescription

    // Function to handle item name changes
    fun onItemNameChanged(newName: String) {
        _itemName.value = newName
    }

    // Function to handle item price changes
    fun onItemPriceChanged(newPrice: String) {
        _itemPrice.value = newPrice
    }

    // Function to handle item description changes
    fun onItemDescriptionChanged(newDescription: String) {
        _itemDescription.value = newDescription
    }

    // Function to upload item to the database
    fun uploadItemToDatabase() {
        val itemName = _itemName.value
        val itemPrice = _itemPrice.value
        val itemDescription = _itemDescription.value

        // Validate input (add your validation logic here)

        // Perform the upload using a coroutine
        viewModelScope.launch {
            try {
                // You can use the values of itemName, itemPrice, itemDescription to build your ArtItemRequest
                // Call your API service here to upload the item
                // Example:
                val artItemRequest = ArtItemRequest(name = itemName, price = itemPrice, image = "", description = itemDescription, userId = "your_user_id_here")
                val response = withContext(Dispatchers.IO){
                    GalleryApi.retrofitService.uploadItem(artItemRequest).execute()
                }
                // Handle the response as needed
                withContext(Dispatchers.Main){
                    if(response.isSuccessful){
                        val uploadRequest = response.body()
                        if (uploadRequest?.isSuccessful == true){
                            Log.d("Upload View Model", "Upload successful")
                        }else{
                            Log.d("Upload View Model", "Upload Failed")
                        }
                    }else{
                        Log.d("Upload View Model", "Failed with HTTP status: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                // Handle the exception (e.g., show an error message)
                e.printStackTrace()
                withContext(Dispatchers.Main){
                    Log.d("Upload View Model", "Upload Failed: ${e.message}")
                }
            }
        }
    }
}


