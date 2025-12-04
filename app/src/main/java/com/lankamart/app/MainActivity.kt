package com.lankamart.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lankamart.app.presentation.navigation.Screen
import com.lankamart.app.presentation.screens.home.MainHomeScreen
import com.lankamart.app.presentation.screens.login.LoginScreen
import com.lankamart.app.presentation.screens.splash.SplashScreen
import com.lankamart.app.presentation.theme.LankaMartTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LankaMartTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.Splash.route
                    ) {
                        composable(Screen.Splash.route) {
                            SplashScreen(navController)
                        }
                        composable(Screen.Login.route) {
                            LoginScreen(navController)
                        }
                        composable(Screen.Signup.route) {
                            // TODO: Create SignupScreen later
                            LoginScreen(navController) // Temporary
                        }
                        composable(Screen.MainHome.route) {
                            MainHomeScreen(navController)
                        }
                    }
                }
            }
        }
    }
}