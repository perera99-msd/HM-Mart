package com.lankamart.app.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.lankamart.app.presentation.theme.DarkTeal
import com.lankamart.app.presentation.theme.SageGreen
import com.lankamart.app.presentation.theme.TextDarkGrey

// 1. Define the Navigation Items
sealed class BottomNavItem(val route: String, val label: String, val activeIcon: ImageVector, val inactiveIcon: ImageVector) {
    object Home : BottomNavItem("home", "Home", Icons.Filled.Home, Icons.Outlined.Home)
    object Cart : BottomNavItem("cart", "Cart", Icons.Filled.ShoppingCart, Icons.Outlined.ShoppingCart)
    object Profile : BottomNavItem("profile", "Profile", Icons.Filled.Person, Icons.Outlined.Person)
}

@Composable
fun LankaMartBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Cart,
        BottomNavItem.Profile
    )

    NavigationBar(
        containerColor = androidx.compose.ui.graphics.Color.White,
        contentColor = DarkTeal
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.route) },
                label = {
                    Text(
                        text = item.label,
                        color = if (isSelected) DarkTeal else TextDarkGrey
                    )
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.activeIcon else item.inactiveIcon,
                        contentDescription = item.label,
                        tint = if (isSelected) DarkTeal else TextDarkGrey
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = SageGreen.copy(alpha = 0.2f) // Subtle selection background
                )
            )
        }
    }
}