package com.lankamart.app.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.lankamart.app.presentation.components.LankaMartBottomBar
import com.lankamart.app.presentation.components.LankaMartTopBar
import com.lankamart.app.presentation.screens.grocery.GroceryHomeScreen
import com.lankamart.app.presentation.screens.onlinestore.OnlineStoreHomeScreen

@Composable
fun MainHomeScreen(
    navController: NavController,
    storeType: String
) {
    var currentBottomRoute by remember { mutableStateOf("home") }
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        containerColor = Color(0xFFF8F9FA),
        topBar = {
            if (currentBottomRoute == "home") {
                LankaMartTopBar(
                    storeType = storeType,
                    selectedTabIndex = selectedTabIndex,
                    onTabSelected = { selectedTabIndex = it },
                    onCartClicked = {
                        navController.navigate("cart")
                    },
                    onNotificationsClicked = {
                        navController.navigate("messages")
                    },
                    navController = navController
                )
            }
        },
        bottomBar = {
            LankaMartBottomBar(
                currentRoute = currentBottomRoute,
                onNavigate = { route ->
                    when (route) {
                        "home" -> currentBottomRoute = "home"
                        "cart" -> navController.navigate("cart")
                        "messages" -> navController.navigate("messages")
                        "offers" -> navController.navigate("offers")
                        "profile" -> navController.navigate("profile")
                    }
                },
                isLoggedIn = true
            )
        }
    ) { paddingValues ->
        // The paddingValues now contain the height of TopBar + Status Bar
        // and BottomBar + Navigation Bar.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FA))
                .padding(paddingValues)
        ) {
            when (currentBottomRoute) {
                "home" -> {
                    if (storeType == "grocery") {
                        GroceryHomeScreen(selectedTabIndex, navController)
                    } else {
                        OnlineStoreHomeScreen(selectedTabIndex, navController)
                    }
                }
                else -> {
                    if (storeType == "grocery") {
                        GroceryHomeScreen(selectedTabIndex, navController)
                    } else {
                        OnlineStoreHomeScreen(selectedTabIndex, navController)
                    }
                }
            }
        }
    }
}