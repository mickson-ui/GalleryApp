package com.example.galleryapp.ui.screens.account

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.galleryapp.network.GalleryApi
import kotlinx.coroutines.launch

class AccountViewModel : ViewModel() {

    private val _userResponse = MutableLiveData<UserResponse?>()
    val userResponse: LiveData<UserResponse?> get() = _userResponse

    // Function to get user details with the login token
    fun getUserDetails(token: String?) {
        viewModelScope.launch {
            try {
                Log.d("AccountViewModel", "Making API call...")
                if (token != null) {
                    // Make the API call with the login token
                    val response = GalleryApi.retrofitService.getUserItems("Bearer $token")

                    if (response.isSuccessful) {
                        Log.d("AccountViewModel", "API call successful")
                        val userDetails = response.body()
                        _userResponse.value = userDetails
                    } else {
                        // Handle error case
                        Log.e("AccountViewModel", "API call failed with response code: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                // Handle exception
                Log.e("AccountViewModel", "Exception during API call", e)
            }
        }
    }
}
