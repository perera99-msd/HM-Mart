package com.lankamart.app.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object HomeChoice : Screen("home_choice")
    object Login : Screen("login")
    object Signup : Screen("signup")

    // Main screens with store type parameter
    object MainHome : Screen("main_home/{storeType}") {
        fun createRoute(storeType: String) = "main_home/$storeType"
    }

    // Bottom nav screens
    object Cart : Screen("cart")
    object Offers : Screen("offers")
    object Messages : Screen("messages")
    object Profile : Screen("profile")
}