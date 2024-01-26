package com.example.galleryapp.ui.screens.upload

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.galleryapp.utils.navigation.BottomBar
import androidx.compose.runtime.getValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadPage(
    uploadViewModel: UploadViewModel = viewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current
    val buttonsVisible = remember { mutableStateOf(true) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                uploadViewModel.photoUri = it
            }
        }
    )
    val itemName by uploadViewModel.itemName
    val itemPrice by uploadViewModel.itemPrice
    val itemDescription by uploadViewModel.itemDescription


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Upload Item") },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp, end = 16.dp, top = 70.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            uploadViewModel.photoUri?.let {
                Image(
                    painter = rememberAsyncImagePainter(model = uploadViewModel.photoUri),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Fit
                )
            }

            TextButton(
                onClick = {
                    launcher.launch("image/*")
                }
            ) {
                Text(
                    text = "Pick image"
                )
            }
            // Input fields
            TextField(
                value = itemName,
                onValueChange = { uploadViewModel.onItemNameChanged(it) },
                label = { Text("Item Name") },
                modifier = Modifier
                    .fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = itemPrice,
                onValueChange = {uploadViewModel.onItemPriceChanged(it) },
                label = { Text("Item Price") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = itemDescription,
                onValueChange = {uploadViewModel.onItemDescriptionChanged(it)},
                label = { Text("Item Description") },
                modifier = Modifier
                    .fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Button to upload item
            Button(
                onClick = { uploadViewModel.uploadItemToDatabase(context)
                          navController.navigate("home") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Upload Item")
            }
        }
    }
    // Update system U
}

@Preview
@Composable
fun UploadPagePreview(){
    UploadPage(navController = rememberNavController(), uploadViewModel = viewModel())
}