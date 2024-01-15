package com.example.galleryapp.ui.screens.itemDetails

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.galleryapp.ui.screens.home.GalleryItem
import com.example.galleryapp.ui.screens.home.dummyData


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailsPage(
    navController: NavHostController,
    itemDetails: GalleryItem,
    onAddToCart: (GalleryItem) -> Unit
) {
    println("Received item ID: ${itemDetails.id}")
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = itemDetails.itemName) },
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
            Image(
                painter = painterResource(id = itemDetails.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Price: ${itemDetails.price}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = itemDetails.description,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = { onAddToCart(itemDetails) },
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
    ItemDetailsPage(itemDetails = dummyData.first(), onAddToCart = {}, navController = navController)
}