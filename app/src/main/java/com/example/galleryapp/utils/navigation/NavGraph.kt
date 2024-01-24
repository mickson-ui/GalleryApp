package com.example.galleryapp.utils.navigation

import Screen
import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.galleryapp.ui.screens.account.AccountPage
import com.example.galleryapp.ui.screens.cart.CartPage
import com.example.galleryapp.ui.screens.cart.dummyCartItems
import com.example.galleryapp.ui.screens.checkout.CheckoutPage
import com.example.galleryapp.ui.screens.home.HomePage
//import com.example.galleryapp.ui.screens.home.dummyData
//import com.example.galleryapp.ui.screens.itemDetails.ItemDetailsPage
import com.example.galleryapp.ui.screens.itemDetails.dummyItemDetails
import com.example.galleryapp.ui.screens.login.LoginPage
import com.example.galleryapp.ui.screens.register.RegisterPage
import com.example.galleryapp.ui.screens.upload.UploadPage


@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun NavGraph(
    navController: NavHostController,
) {
    var currentRoute by remember { mutableStateOf(Screen.HomePage.route) }
    NavHost(
        navController = navController,
        startDestination = Screen.LoginPage.route
    ) {
        composable(Screen.HomePage.route) {
            currentRoute = Screen.HomePage.route
            HomePage(navController = navController,
                selectedItem = { selectedItem ->
                    navController.navigate("${Screen.ItemDetailsPage.route}/$selectedItem")
                }/*, onFavoriteClicked = {}*/)
        }
//        composable(Screen.ItemDetailsPage.route + "/{selectedItemId}") { backStackEntry ->
//            val selectedItemId =
//                backStackEntry.arguments?.getString("selectedItemId")?.toIntOrNull() ?: 0
//            val selectedItem = dummyData.find { it.id == selectedItemId } ?: dummyData.first()
//
//            ItemDetailsPage(
//                navController = navController,
//                itemDetails = selectedItem,
//                onAddToCart = {})
//        }
        composable(Screen.CartPage.route) {
            currentRoute = Screen.CartPage.route
            CartPage(
                navController = navController,
                cartItems = dummyCartItems,
                onCheckoutClicked = {},
            )
        }
        composable(Screen.CheckoutPage.route) {
            currentRoute = Screen.CheckoutPage.route
            CheckoutPage(navController = navController)
        }
        composable(Screen.UploadPage.route) {
            currentRoute = Screen.UploadPage.route
            UploadPage(navController = navController, uploadViewModel = viewModel())
        }
        composable(Screen.AccountPage.route) {
            currentRoute = Screen.AccountPage.route
            AccountPage(navController = navController)
        }
        composable(Screen.LoginPage.route) {
            LoginPage(navController = navController)
        }
        composable(Screen.RegisterPage.route) {
            RegisterPage(navController = navController)
        }
        // Bottom Navigation Bar}
    }
}

@Composable
fun BottomBar(
    navController: NavHostController, state: MutableState<Boolean>, modifier: Modifier = Modifier
) {
    val screens = listOf(
        Screen.HomePage, Screen.CartPage, Screen.UploadPage, Screen.AccountPage
    )

    NavigationBar(
        modifier = modifier,
        containerColor = Color.LightGray,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->

            NavigationBarItem(
                label = {
                    Text(text = screen.label)
                },
                icon = {
                    Icon(imageVector = screen.icon, contentDescription = "")
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedTextColor = Color.Gray, selectedTextColor = Color.White
                ),
            )
        }
    }
}

@Preview
@Composable
fun BottomBarPreview() {
    val navController = rememberNavController()
    val buttonsVisible = remember { mutableStateOf(true) }
    BottomBar(navController = navController, buttonsVisible)
}
