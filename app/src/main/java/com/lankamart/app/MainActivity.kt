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
import com.lankamart.app.presentation.navigation.Screen
import com.lankamart.app.presentation.screens.cart.CartScreen
import com.lankamart.app.presentation.screens.home.MainHomeScreen
import com.lankamart.app.presentation.screens.homechoice.HomeChoiceScreen
import com.lankamart.app.presentation.screens.login.LoginScreen
import com.lankamart.app.presentation.screens.messages.MessagesScreen
import com.lankamart.app.presentation.screens.offers.OffersScreen
import com.lankamart.app.presentation.screens.profile.ProfileScreen
import com.lankamart.app.presentation.screens.signup.SignupScreen
import com.lankamart.app.presentation.screens.splash.SplashScreen
import com.lankamart.app.presentation.theme.LankaMartTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Make activity full screen with edge-to-edge
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
                        startDestination = Screen.Splash.route
                    ) {
                        composable(Screen.Splash.route) {
                            SplashScreen(navController = navController)
                        }

                        composable(Screen.HomeChoice.route) {
                            HomeChoiceScreen(navController = navController)
                        }

                        composable(Screen.Login.route) {
                            LoginScreen(navController = navController)
                        }

                        composable(Screen.Signup.route) {
                            SignupScreen(navController = navController)
                        }

                        composable(
                            route = Screen.MainHome.route,
                            arguments = listOf(
                                navArgument("storeType") {
                                    type = NavType.StringType
                                }
                            )
                        ) { backStackEntry ->
                            val storeType = backStackEntry.arguments?.getString("storeType") ?: "grocery"
                            MainHomeScreen(
                                navController = navController,
                                storeType = storeType
                            )
                        }

                        composable(Screen.Cart.route) {
                            CartScreen(navController = navController)
                        }

                        composable(Screen.Offers.route) {
                            OffersScreen(navController = navController)
                        }

                        composable(Screen.Messages.route) {
                            MessagesScreen(navController = navController)
                        }

                        composable(Screen.Profile.route) {
                            ProfileScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}