package com.example.galleryapp.ui.screens.itemDetails

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.galleryapp.network.GalleryApi
import kotlinx.coroutines.launch

class ItemViewModel : ViewModel() {
    private val _arts = mutableStateOf<ItemDetailsResponse?>(null)
    val arts: State<ItemDetailsResponse?> get() = _arts

    // Function to get user details with the login token
    fun getItemDetails(token: String?, selectedItemId: State<String>) {
        viewModelScope.launch {
            try {
                Log.d("ItemViewModel", "Making API call...")
                if (token != null) {
                    // Make the API call with the login token and selectedItemId
                    val response = GalleryApi.retrofitService.getItemDetails(selectedItemId)

                    if (response.isSuccessful) {
                        Log.d("ItemViewModel", "API call successful")
                        val userDetails = response.body()
                        _arts.value = userDetails
                        Log.d("ItemViewModel", "User details: $userDetails")
                    } else {
                        // Handle error case
                        Log.e("ItemViewModel", "API call failed with response code: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                // Handle exception
                Log.e("ItemViewModel", "Exception during API call", e)
            }
        }
    }
}
