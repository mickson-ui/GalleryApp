package com.example.galleryapp.ui.screens.itemDetails

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.galleryapp.R
import com.example.galleryapp.utils.ImageConverter


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailsPage(
    navController: NavHostController,
    viewModel: ItemViewModel = viewModel(),
    selectedItem: State<String>,
    onAddToCart: () -> Unit
) {
    val arts by remember { viewModel.itemDetails }

    val itemId: String = selectedItem.value

    LaunchedEffect(itemId) {
        viewModel.getItem(selectedItemId = itemId)
    }

    Log.d("Item details page", "ID from home: $itemId")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Item Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            // Call the deleteItem function when delete button is clicked
                            viewModel.deleteItem(selectedItemId = itemId)
                            // Navigate back to the home page
                            navController.popBackStack()
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 75.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            if (arts != null) {
                Log.d("ItemDetailsPage", "ItemResponse is not null")

                val art = arts?.art
                val bitmap = art?.let { it1 -> ImageConverter().base64ToImageBitmap(it1.image) }
                Log.d("Items from details", "items $art")

                Log.d("Item DetailsPage", "arts is not empty")
                // Accessing the properties only if the data is not null or empty
                bitmap?.let {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    ) {
                        Image(
                            bitmap = it,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Text(
                    text = "Price: ${art?.price}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                art?.description?.let { it1 ->
                    Text(
                        text = it1,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

            } else {
                Log.d("Item DetailsPage", "Item Response is null")
                // Handle the case where userResponse is null
                Text(text = "No data available")
            }

            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = { onAddToCart() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Add to Cart")
            }
        }
    }
}

@Preview
@Composable
fun ItemDetailsPagePreview() {
    val navController = rememberNavController()
    val selectedItemState = remember { mutableStateOf("your_initial_value_here") }
    ItemDetailsPage(selectedItem = selectedItemState, onAddToCart = {}, navController = navController)
}