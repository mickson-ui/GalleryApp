package com.example.galleryapp.ui.screens.itemDetails

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailsPage(
    navController: NavHostController,
    viewModel: ItemViewModel = viewModel(),
    selectedItem: State<String>,
    onAddToCart: () -> Unit
) {
    val arts by remember { viewModel.arts }

    LaunchedEffect(selectedItem) {
        viewModel.getItemDetails(token = null, selectedItemId = selectedItem)
    }
    Log.d("Selected Item ID", "ID from at details: $selectedItem")
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = arts?.responseMessage?: "Item Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(
                        onClick = { /* Handle favorite action */ }
                    ) {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
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
//            Image(
//                painter = painterResource(id = userResponse?.arts?.firstOrNull()?.imageUrl ?: R.drawable.img1),
//                contentDescription = null,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(200.dp)
//                    .clip(RoundedCornerShape(8.dp)),
//                contentScale = ContentScale.Crop
//            )
            Spacer(modifier = Modifier.height(16.dp))
            if (arts != null) {
                Log.d("ItemDetailsPage", "ItemResponse is not null")

                val art = arts!!.arts
                Log.d("Items from details", "items $art")

                if (art.isNotEmpty()) {
                    Log.d("Item DetailsPage", "arts is not empty")
                    // Accessing the properties only if the data is not null or empty
                    Text(
                        text = "Price: ${art.first().price}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = art.first().description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    Log.d("ItemDetailsPage", "arts is empty")
                    // Handle the case where the data is empty
                    Text(text = "No data available")
                }
            } else {
                Log.d("Item DetailsPage", "ItemResponse is null")
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