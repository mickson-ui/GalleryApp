package com.example.galleryapp.ui.screens.itemDetails

import com.example.galleryapp.ui.screens.account.UserResponse
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.galleryapp.network.GalleryApi
import kotlinx.coroutines.launch

class ItemViewModel : ViewModel() {

    private val _userResponse = MutableLiveData<ItemDetailsResponse?>()
    val userResponse: LiveData<ItemDetailsResponse?> get() = _userResponse

    // Function to get user details with the login token
    fun getUserDetails(token: String?) {
        viewModelScope.launch {
            try {
                Log.d("ItemViewModel", "Making API call...")
                if (token != null) {
                    // Make the API call with the login token
                    val response = GalleryApi.retrofitService.getItemDetails()

                    if (response.isSuccessful) {
                        Log.d("ItemViewModel", "API call successful")
                        val userDetails = response.body()
                        _userResponse.value = userDetails
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
