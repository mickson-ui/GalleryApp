package com.example.galleryapp.ui.screens.home

import Screen
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.galleryapp.utils.navigation.BottomBar
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.galleryapp.utils.ImageConverter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    navController: NavHostController,
    viewModel: HomeViewModel = viewModel()
) {
    val buttonsVisible = remember { mutableStateOf(true) }
    val arts by remember { viewModel.arts }

    // Trigger state for refreshing the home page
    var refreshTrigger by remember { mutableStateOf(false) }

    // Call this function to refresh the items
    fun refreshItems() {
        viewModel.refreshItems()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Gallery App") },
                actions = {
                    IconButton(
                        onClick = {
                            // Set refreshTrigger to true to trigger the refresh
                            refreshTrigger = true
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                    }
                    IconButton(
                        onClick = { navController.navigate(Screen.LoginPage.route)}
                    ) {
                        Icon(imageVector = Icons.Default.ExitToApp, contentDescription = null)
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
        // Observe the refreshTrigger value and refresh the items when it changes
        LaunchedEffect(refreshTrigger) {
            if (refreshTrigger) {
                refreshItems()
                refreshTrigger = false
            }
        }
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(top = 65.dp)
//                .verticalScroll(rememberScrollState())
        ) {
            items(arts) { art ->
                GalleryCard(
                    galleryItem = art,
                ) {
                    println("Selected item ID from home: ${art.id}")
                    navController.navigate("${Screen.ItemDetailsPage.route}/${art.id}")
                }
            }
        }

    }
}


@Composable
fun GalleryCard(
    galleryItem: Art,
    onItemSelected: () -> Unit,
) {
    val bitmap = ImageConverter().base64ToImageBitmap(galleryItem.image)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemSelected() },
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
                bitmap?.let {
                    Image(
                        bitmap = it,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = galleryItem.name, style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Price: ${galleryItem.price}", style = MaterialTheme.typography.displaySmall)
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}


@Preview
@Composable
fun HomePagePreview(){
    val navController = rememberNavController()
    HomePage( /*onFavoriteClicked = {},*/ navController = navController)
}