package com.example.galleryapp.ui.screens.home

import Screen
import android.annotation.SuppressLint
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.galleryapp.utils.navigation.BottomBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    navController: NavHostController,
    selectedItem: (GalleryItem) -> Unit,
    onFavoriteClicked: (Int) -> Unit
) {
    val buttonsVisible = remember { mutableStateOf(true) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Gallery App") },
                actions = {
                    IconButton(
                        onClick = { navController.navigate(Screen.CartPage.route) }
                    ) {
                        Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null)
                    }
                }
            )
        },
        bottomBar = {
            BottomBar(
                navController = navController,
                state = buttonsVisible,
                modifier = Modifier
            )
        }
    ) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(top = 65.dp)
        ) {
            items(dummyData) { item ->
                GalleryCard(
                    galleryItem = item,
                    onItemSelected = { selectedItem->
                        println("Selected item ID: ${selectedItem.id}")
                        navController.navigate("${Screen.ItemDetailsPage.route}/${selectedItem.id.toString()}")
                    },
                    onFavoriteClicked = onFavoriteClicked
                )
            }
        }
    }
}

@Composable
fun GalleryCard(
    galleryItem: GalleryItem,
    onItemSelected: (GalleryItem) -> Unit,
    onFavoriteClicked: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemSelected(galleryItem) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),

    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Image(
                    painter = painterResource(id = galleryItem.imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = galleryItem.itemName, style = MaterialTheme.typography.labelSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Price: ${galleryItem.price}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { onFavoriteClicked(galleryItem.id) },
                    modifier = Modifier
                        .background(
                            color = if (galleryItem.isFavorite) Color.Red else Color.Transparent,
                            shape = CircleShape
                        )
                ) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
                }
            }
        }
    }
}

@Preview
@Composable
fun HomePagePreview(){
    val navController = rememberNavController()
    HomePage(selectedItem = {}, onFavoriteClicked = {}, navController = navController)
}