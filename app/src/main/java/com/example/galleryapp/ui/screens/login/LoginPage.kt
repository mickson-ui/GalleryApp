package com.example.galleryapp.ui.screens.login

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.galleryapp.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
                // Handle login logic here
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
}

@Preview
@Composable
fun LoginPagePreview(){
    LoginPage(navController = rememberNavController())
}
