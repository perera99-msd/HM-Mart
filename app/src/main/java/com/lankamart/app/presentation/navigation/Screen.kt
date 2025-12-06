// Screen.kt
package com.lankamart.app.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object HomeChoice : Screen("home_choice")
    object Login : Screen("login")
    object Signup : Screen("signup")
    object MainHome : Screen("main_home/{storeType}") {
        fun createRoute(storeType: String) = "main_home/$storeType"
    }
    object Cart : Screen("cart")
    object Offers : Screen("offers")
    object Messages : Screen("messages")
    object Profile : Screen("profile")
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: String) = "product_detail/$productId"
    }
    object Checkout : Screen("checkout")
    object OrderSuccess : Screen("order_success")
    object Search : Screen("search")
    object CategoryProducts : Screen("category/{categoryId}") {
        fun createRoute(categoryId: String) = "category/$categoryId"
    }
}