package com.lankamart.app.presentation.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.lankamart.app.presentation.components.LankaMartBottomBar
import com.lankamart.app.presentation.components.LankaMartTopBar

@Composable
fun MainHomeScreen(navController: NavController) {
    // State to manage which Bottom Nav item is selected
    var currentRoute by remember { mutableStateOf("home") }

    // State to manage the Top Tab (0 = Online Store, 1 = Grocery Store)
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            // Only show Top Bar if we are on the 'Home' tab of bottom nav
            if (currentRoute == "home") {
                LankaMartTopBar(
                    selectedTabIndex = selectedTabIndex,
                    onTabSelected = { index -> selectedTabIndex = index }
                )
            }
        },
        bottomBar = {
            LankaMartBottomBar(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    currentRoute = route
                    // You can also navigate using navController if needed
                }
            )
        }
    ) { paddingValues ->
        // The main content area
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            // Placeholder content based on selection
            when (currentRoute) {
                "home" -> {
                    if (selectedTabIndex == 0) {
                        Text("Online Store Content Goes Here")
                    } else {
                        Text("Grocery Store Content Goes Here")
                    }
                }
                "cart" -> Text("Shopping Cart Screen")
                "profile" -> Text("User Profile Screen")
            }
        }
    }
}