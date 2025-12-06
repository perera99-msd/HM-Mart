// MainActivity.kt
package com.lankamart.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lankamart.app.presentation.screens.cart.CartScreen
import com.lankamart.app.presentation.screens.checkout.CheckoutScreen
import com.lankamart.app.presentation.screens.home.MainHomeScreen
import com.lankamart.app.presentation.screens.homechoice.HomeChoiceScreen
import com.lankamart.app.presentation.screens.login.LoginScreen
import com.lankamart.app.presentation.screens.messages.MessagesScreen
import com.lankamart.app.presentation.screens.offers.OffersScreen
import com.lankamart.app.presentation.screens.ordersuccess.OrderSuccessScreen
import com.lankamart.app.presentation.screens.productdetail.ProductDetailScreen
import com.lankamart.app.presentation.screens.profile.ProfileScreen
import com.lankamart.app.presentation.screens.search.SearchScreen
import com.lankamart.app.presentation.screens.signup.SignupScreen
import com.lankamart.app.presentation.screens.splash.SplashScreen
import com.lankamart.app.presentation.theme.LankaMartTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Enable full edge-to-edge layout
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            LankaMartTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "splash"
                    ) {
                        composable("splash") {
                            SplashScreen(navController = navController)
                        }
                        composable("home_choice") {
                            HomeChoiceScreen(navController = navController)
                        }
                        composable("login") {
                            LoginScreen(navController = navController)
                        }
                        composable("signup") {
                            SignupScreen(navController = navController)
                        }
                        composable(
                            "main_home/{storeType}",
                            arguments = listOf(navArgument("storeType") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val storeType = backStackEntry.arguments?.getString("storeType") ?: "grocery"
                            MainHomeScreen(
                                navController = navController,
                                storeType = storeType
                            )
                        }
                        composable("cart") {
                            CartScreen(navController = navController)
                        }
                        composable("offers") {
                            OffersScreen(navController = navController)
                        }
                        composable("messages") {
                            MessagesScreen(navController = navController)
                        }
                        composable("profile") {
                            ProfileScreen(navController = navController)
                        }
                        composable(
                            "product_detail/{productId}",
                            arguments = listOf(navArgument("productId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val productId = backStackEntry.arguments?.getString("productId") ?: ""
                            ProductDetailScreen(
                                navController = navController,
                                productId = productId
                            )
                        }
                        composable("checkout") {
                            CheckoutScreen(navController = navController)
                        }
                        composable("order_success") {
                            OrderSuccessScreen(navController = navController)
                        }
                        composable("search") {
                            SearchScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}