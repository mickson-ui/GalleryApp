package com.example.galleryapp.ui.screens.home

import Screen
import android.annotation.SuppressLint
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.galleryapp.utils.navigation.BottomBar
import androidx.lifecycle.viewmodel.compose.viewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    navController: NavHostController,
    onItemSelected: (String) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val buttonsVisible = remember { mutableStateOf(true) }
    val arts by remember { viewModel.arts }

    LaunchedEffect(viewModel){
        viewModel.arts
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Gallery App") },
                actions = {
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
                ) { onItemSelected ->
                    println("Selected item ID from home: ${onItemSelected.id}")
                    navController.navigate("${Screen.ItemDetailsPage.route}/${onItemSelected}")
                }
            }
        }

    }
}


@Composable
fun GalleryCard(
    galleryItem: Art,
    onItemSelected: (Art) -> Unit,
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
//                Image(
//                    painter = painterResource(id = galleryItem.id.toInt()),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .fillMaxHeight()
//                        .clip(RoundedCornerShape(8.dp)),
//                    contentScale = ContentScale.Crop
//                )
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
    HomePage(onItemSelected = {}, /*onFavoriteClicked = {},*/ navController = navController)
}