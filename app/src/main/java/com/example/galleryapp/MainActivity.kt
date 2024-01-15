package com.example.galleryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.galleryapp.ui.theme.GalleryAppTheme
import androidx.navigation.compose.rememberNavController
import com.example.galleryapp.ui.screens.home.HomePage
import com.example.galleryapp.utils.navigation.NavGraph


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GalleryAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = colorResource(id = R.color.black)
                ) {
                    HomeScreen()
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    // Create a NavHostController
    val navController = rememberNavController()
    // Provide the navController to the NavGraph
    NavGraph(navController = navController)
}

@Composable
@Preview(showBackground = true)
fun DefaultPreview() {
    GalleryAppTheme {
        val navController = rememberNavController()
        HomeScreen()
    }
}

