import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val icon: ImageVector, val label: String) {
    object LoginPage : Screen("login", Icons.Default.Email, "Login")
    object RegisterPage : Screen("register", Icons.Default.Email, "Register")
    object HomePage : Screen("home", Icons.Default.Home, "Home")
    object ItemDetailsPage : Screen("itemDetails", Icons.Default.Info, "Details")
    object CartPage : Screen("cart", Icons.Default.ShoppingCart, "Cart")
    object CheckoutPage : Screen("checkout", Icons.Default.ExitToApp, "Checkout")
    object UploadPage : Screen("upload", Icons.Default.AddCircle, "Upload")
    object AccountPage : Screen("account", Icons.Default.AccountCircle, "Account")
}

//sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
//    object HomePage : Screen("home", Icons.Default.Home, "Home")
//    object CartPage : Screen("cart", Icons.Default.ShoppingCart, "Cart")
//    object CheckoutPage : Screen("checkout", Icons.Default.ExitToApp, "Checkout")
//}