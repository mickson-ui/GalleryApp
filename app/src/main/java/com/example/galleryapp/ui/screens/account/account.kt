package com.example.galleryapp.ui.screens.account

import SessionManager
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.galleryapp.utils.navigation.BottomBar
import retrofit2.Call
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.galleryapp.network.GalleryApi

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountPage(
    navController: NavHostController,
    viewModel: AccountViewModel = viewModel()
    ) {
    val buttonsVisible = remember { mutableStateOf(true) }
    val sessionManager = SessionManager(LocalContext.current)
    val token = sessionManager.token

    var userResponse by remember { mutableStateOf<UserResponse?>(null) }
    // Trigger API call when the composition is first launched
    LaunchedEffect(token) {
        viewModel.getUserDetails(token)
    }

    DisposableEffect(viewModel.userResponse) {
        val observer = Observer<UserResponse?> { response ->
            userResponse = response
        }

        val liveData = viewModel.userResponse
        liveData.observeForever(observer)

        onDispose {
            liveData.removeObserver(observer)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Account") },
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
            // Display account details
            userResponse?.let { userResponseValue ->
                // Display user details and arts
                Text("User ID: ${userResponseValue.user.email}")
                Text("User Name: ${userResponseValue.user.fullName}")
                Text("Email: ${userResponseValue.user.email}")

                Spacer(modifier = Modifier.height(16.dp))

                userResponseValue.arts.forEach { art ->
                    Text("Art ID: ${art.id}")
                    Text("Name: ${art.name}")
                    Text("Price: ${art.price}")
                    // Add other art details as needed
                    Spacer(modifier = Modifier.height(16.dp))
                }
            } ?: run {
                // Handle loading or error state
                Text("Loading user details...")
            }
        }
    }
}

@Composable
fun <T> rememberApiCall(call: Call<T>): State<T?> {
    var result by remember { mutableStateOf<T?>(null) }

    DisposableEffect(call) {
        val response = executeApiCall(call)
        if (response != null && response.isSuccessful) {
            result = response.body()
        }
        onDispose { /* cleanup code if needed */ }
    }
    return rememberUpdatedState(result)
}
private fun <T> executeApiCall(call: Call<T>): retrofit2.Response<T>? {
    return try {
        call.execute()
    } catch (e: Exception) {
        // Handle exceptions (e.g., IOException)
        e.printStackTrace()
        null
    }
}


@Preview
@Composable
fun AccountPagePreview(){
    val navController = rememberNavController()
    AccountPage(navController = navController)
}