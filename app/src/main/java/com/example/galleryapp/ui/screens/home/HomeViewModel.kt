package com.example.galleryapp.ui.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.util.Log
import com.example.galleryapp.network.GalleryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    private val _arts = mutableStateOf<List<Art>>(emptyList())
    val arts: State<List<Art>> = _arts

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("HomeViewModel", "Making API call...")

                val response = GalleryApi.retrofitService.getAllArt()

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Log.d("HomeViewModel", "API call successful")
                        val artsResponse = response.body()
                        artsResponse?.let {
                            _arts.value = it.arts
                        }
                    } else {
                        Log.e("HomeViewModel", "API call failed with response code: ${response.code()}")
                        // Handle error case
                        // You can set an error state or log the error
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("HomeViewModel", "Exception during API call", e)
                    // Handle exception
                }
            }
        }
    }

    fun refreshItems() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("HomeViewModel", "Refreshing items...")

                val response = GalleryApi.retrofitService.getAllArt()

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Log.d("HomeViewModel", "Refresh successful")
                        val artsResponse = response.body()
                        artsResponse?.let {
                            _arts.value = it.arts
                        }
                    } else {
                        Log.e("HomeViewModel", "Refresh failed with response code: ${response.code()}")
                        // Handle error case
                        // You can set an error state or log the error
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("HomeViewModel", "Exception during refresh", e)
                    // Handle exception
                }
            }
        }
    }

}
