package com.example.galleryapp.ui.screens.login

import SessionManager
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.galleryapp.R
import com.example.galleryapp.network.GalleryApi
import com.example.galleryapp.network.LoginUser
import com.example.galleryapp.ui.screens.register.User
import com.example.galleryapp.utils.navigation.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current


    val authService = AuthService()
//    val snackbarHostState = remember {SnackbarHostState()}

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(id = R.string.email)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(id = R.string.password)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                // Handle registration logic here
                val user = LoginUser(email = email, password = password)
                // Using GlobalScope.launch for simplicity, consider using a viewModel and viewModelScope in a real app
                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        val response = GalleryApi.retrofitService.loginUser(user).execute()
                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful) {
                                val registrationResponse = response.body()
                                if (registrationResponse?.isSuccessful == true) {
                                    SessionManager(context = context).apply {
                                        token = registrationResponse.tokenDto.token
                                        email = registrationResponse.user.email
                                    }
                                    Toast.makeText(
                                        context,
                                        "Login successful",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    // Check if token is saved before navigating
                                    if (SessionManager(context = context).isTokenSaved) {
                                        navController.navigate("home")
                                    } else {
                                        showErrorDialog = true
                                    }
//                                    navController.navigate("home")
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Login failed: ${registrationResponse?.responseMessage}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                // Handle other HTTP status codes if needed
                                Toast.makeText(
                                    context,
                                    "Login failed with HTTP status: ${response.code()}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } catch (e: IOException) {
                        // Handle network or I/O exceptions
                        e.printStackTrace()

                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                "Login failed: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: Exception) {
                        // Handle other exceptions
                        e.printStackTrace()

                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                "Login failed: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.blue)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(stringResource(id = R.string.login))
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            onClick = {
                navController.navigate("register")
            }
        ) {
            Text(
                stringResource(id = R.string.create_account),
                color = colorResource(id = R.color.blue)
            )
        }
    }
    if (showErrorDialog) {
        ErrorDialog(
            onDismiss = { showErrorDialog = false },
            message = "Token not saved"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorDialog(
    onDismiss: () -> Unit,
    message: String
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text("Error")
        },
        text = {
            Text(message)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    { onDismiss() }
                }
            ) {
//                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismiss() }
            ) {
                Text("OK")
            }
        }
    )
}

@Preview
@Composable
fun LoginPagePreview(){
    LoginPage(navController = rememberNavController())
}
