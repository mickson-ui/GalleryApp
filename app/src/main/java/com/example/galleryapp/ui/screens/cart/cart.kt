package com.example.galleryapp.ui.screens.cart

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.galleryapp.ui.screens.home.GalleryCard
import com.example.galleryapp.ui.screens.home.GalleryItem
import com.example.galleryapp.utils.navigation.BottomBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartPage(
    navController: NavHostController,
    cartItems: List<CartItem>,
    onCheckoutClicked: () -> Unit
) {
    val buttonsVisible = remember { mutableStateOf(true) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Shopping Cart") },
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
                .padding(top = 70.dp)
        ) {
            items(cartItems) { cartItem ->
                GalleryCard(
                    galleryItem = GalleryItem(
                        id = cartItem.id,
                        itemName = cartItem.itemName,
                        price = cartItem.price,
                        imageUrl = cartItem.imageUrl,
                        isFavorite = false,
                        description = "This is a sample item description. Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    ),
                    onItemSelected = { /* Handle item click in the cart */ },
                    onFavoriteClicked = { /* Handle favorite action */ }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onCheckoutClicked() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Proceed to Checkout")
        }
    }
}

@Preview
@Composable
fun CartPagePreview() {
    val navController = rememberNavController()
    CartPage(cartItems = dummyCartItems, onCheckoutClicked = {}, navController = navController)
}