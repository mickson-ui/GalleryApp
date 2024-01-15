package com.example.galleryapp.ui.screens.checkout

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.galleryapp.ui.screens.cart.CartPage

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutPage(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Checkout") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        // Display checkout information and include a "Place Order" button
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Checkout Information", style = MaterialTheme.typography.labelMedium)
            // Add fields for user details, payment information, etc.
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /* Handle place order action */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Place Order")
            }
        }
    }
}

@Preview
@Composable
fun CheckoutPagePreview(){
    val navController = rememberNavController()
    CheckoutPage(navController = navController)
}