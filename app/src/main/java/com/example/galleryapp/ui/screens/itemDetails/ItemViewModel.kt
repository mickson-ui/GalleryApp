package com.example.galleryapp.ui.screens.itemDetails

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.galleryapp.network.GalleryApi
import kotlinx.coroutines.launch

class ItemViewModel : ViewModel() {
    private val _itemDetails = mutableStateOf<ItemDetailsResponse?>(null)
    val itemDetails: State<ItemDetailsResponse?> get() = _itemDetails

    private val _delete = mutableStateOf<DeleteResponse?>(null)
    val delete: State<DeleteResponse?> get() = _delete

    // Function to get item details with the login token
    fun getItem(selectedItemId: String) {
        viewModelScope.launch {
            try {
                Log.d("ItemViewModel", "Making API call...")
                // Make the API call with the login token and selectedItemId
                val response = GalleryApi.retrofitService.getItemDetails(selectedItemId)

                if (response.isSuccessful) {
                    Log.d("ItemViewModel", "API call successful")
                    val itemDetailsResponse = response.body()
                    _itemDetails.value = itemDetailsResponse
                    Log.d("ItemViewModel", "Item details: $itemDetailsResponse")
                } else {
                    // Handle error case
                    val errorBody = response.errorBody()?.string()
                    Log.e("ItemViewModel", "API call failed with response code: ${response.code()}, errorBody: $errorBody")
                }
            } catch (e: Exception) {
                // Handle exception
                Log.e("ItemViewModel", "Exception during API call", e)
            }
        }
    }

    // Function to delete an item
    fun deleteItem(selectedItemId: String) {
        viewModelScope.launch {
            try {
                Log.d("ItemViewModel", "Deleting item with ID: $selectedItemId")
                val response = GalleryApi.retrofitService.deleteItem(selectedItemId)

                if (response.isSuccessful) {
                    Log.d("ItemViewModel", "Item deletion successful")
                    val deleteResponse = response.body()
                    _delete.value = deleteResponse
                    Log.d("ItemViewModel", "Delete response: $deleteResponse")
                } else {
                    // Handle error case
                    val errorBody = response.errorBody()?.string()
                    Log.e("ItemViewModel", "Item deletion failed with response code: ${response.code()}, errorBody: $errorBody")
                }
            } catch (e: Exception) {
                // Handle exception
                Log.e("ItemViewModel", "Exception during item deletion", e)
            }
        }
    }
}
