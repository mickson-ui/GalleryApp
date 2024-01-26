package com.example.galleryapp.ui.screens.upload

import SessionManager
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.galleryapp.network.GalleryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.galleryapp.utils.ImageConverter


class UploadViewModel: ViewModel() {
    // State variables to observe in the UI
    private val _itemName = mutableStateOf("")
    val itemName get() = _itemName

    private val _itemPrice = mutableStateOf("")
    val itemPrice get() = _itemPrice

    private val _itemDescription = mutableStateOf("")
    val itemDescription get() = _itemDescription

    var photoUri: Uri? by   mutableStateOf(null)

    fun onItemNameChanged(newName: String) {
        _itemName.value = newName
    }
    fun onItemPriceChanged(newPrice: String) {
        _itemPrice.value = newPrice
    }
    fun onItemDescriptionChanged(newDescription: String) {
        _itemDescription.value = newDescription
    }

    fun uploadItemToDatabase(context: Context) {
        val itemName = _itemName.value
        val itemPrice = _itemPrice.value
        val itemDescription = _itemDescription.value
        val imageBase64 = photoUri?.let { ImageConverter().uriToBase64(context, it) }
        val  userID = SessionManager(context).userId

        // Validate input (add your validation logic here)
        viewModelScope.launch {
            try {
                val artItemRequest = ArtItemRequest(name = itemName, price = itemPrice, image = imageBase64 ?: "", description = itemDescription, userId = userID ?: "")
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


