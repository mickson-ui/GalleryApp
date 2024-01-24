package com.example.galleryapp.ui.screens.upload

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadPage(
    uploadViewModel: UploadViewModel = viewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current
    val buttonsVisible = remember { mutableStateOf(true) }
    //The URI of the photo that the user has picked
    var photoUri: Uri? by remember { mutableStateOf(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        //When the user has selected a photo, its URI is returned here
        photoUri = uri
    }
    val itemName by uploadViewModel.itemName
    val itemPrice by uploadViewModel.itemPrice
    val itemDescription by uploadViewModel.itemDescription

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Upload Item") },
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
            Button(
                onClick = {
                    //On button press, launch the photo picker
                    launcher.launch(PickVisualMediaRequest(
                        //Here we request only photos. Change this to .ImageAndVideo if
                        //you want videos too.
                        //Or use .VideoOnly if you only want videos.
                        mediaType = ActivityResultContracts.PickVisualMedia.ImageAndVideo
                    )
                    )
                }
            ) {
                Text("Select Photo")
            }
            if (photoUri != null) {
                //Use Coil to display the selected image
                val painter = rememberAsyncImagePainter(
                    ImageRequest
                        .Builder(LocalContext.current)
                        .data(data = photoUri)
                        .build()
                )
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .border(6.0.dp, Color.Gray),
                    contentScale = ContentScale.Crop
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
                onClick = { uploadViewModel.uploadItemToDatabase() },
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