// LankaMartBottomBar.kt
package com.lankamart.app.presentation.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lankamart.app.presentation.theme.*

@Composable
fun LankaMartBottomBar(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    isLoggedIn: Boolean
) {
    val items = listOf(
        BottomNavItem("home", "Home", Icons.Default.Home),
        BottomNavItem("offers", "Offers", Icons.Default.LocalOffer),
        BottomNavItem("cart", "Cart", Icons.Default.ShoppingCart),
        BottomNavItem("messages", "Messages", Icons.Default.Notifications),
        BottomNavItem("profile", "Profile", Icons.Default.Person)
    )

    // Using Surface to ensure background color extends behind the nav bar area
    Surface(
        color = Color.White,
        tonalElevation = 8.dp
    ) {
        NavigationBar(
            // FIX: Remove fixed height, use navigationBarsPadding
            modifier = Modifier.navigationBarsPadding(),
            containerColor = Color.White,
            tonalElevation = 0.dp // Elevation handled by Surface
        ) {
            items.forEach { item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            fontFamily = Poppins,
                            fontSize = 10.sp
                        )
                    },
                    selected = currentRoute == item.route,
                    onClick = { onNavigate(item.route) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = DarkTeal,
                        selectedTextColor = DarkTeal,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent // Removes the pill shape background
                    )
                )
            }
        }
    }
}

data class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)