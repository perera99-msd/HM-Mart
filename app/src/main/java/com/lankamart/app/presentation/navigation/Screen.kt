package com.lankamart.app.presentation.navigation

sealed class Screen(val route: String) {

    object Splash : Screen("splash_screen")

    // New Home Choice Screen (shown after splash)
    object HomeChoice : Screen("home_choice_screen")

    object Login : Screen("login_screen")
    object Signup : Screen("signup_screen")

    object MainHome : Screen("main_home_screen") // Contains the Bottom Bar

    object ProductDetail : Screen("product_detail_screen")
    object Cart : Screen("cart_screen")
}
