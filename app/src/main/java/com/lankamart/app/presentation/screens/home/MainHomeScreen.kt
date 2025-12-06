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
import com.lankamart.app.presentation.navigation.Screen
import com.lankamart.app.presentation.screens.grocery.GroceryHomeScreen
import com.lankamart.app.presentation.screens.onlinestore.OnlineStoreHomeScreen
import kotlinx.coroutines.launch

@Composable
fun MainHomeScreen(
    navController: NavController,
    storeType: String
) {
    var currentBottomRoute by remember { mutableStateOf("home") }
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = Color(0xFFF8F9FA),
        topBar = {
            if (currentBottomRoute == "home") {
                LankaMartTopBar(
                    storeType = storeType,
                    selectedTabIndex = selectedTabIndex,
                    onTabSelected = { selectedTabIndex = it },
                    onCartClicked = {
                        scope.launch {
                            navController.navigate(Screen.Cart.route)
                        }
                    },
                    onNotificationsClicked = {
                        scope.launch {
                            navController.navigate(Screen.Messages.route)
                        }
                    }
                )
            }
        },
        bottomBar = {
            LankaMartBottomBar(
                currentRoute = currentBottomRoute,
                onNavigate = { route ->
                    when (route) {
                        "home" -> {
                            currentBottomRoute = "home"
                        }
                        "cart" -> {
                            navController.navigate(Screen.Cart.route)
                        }
                        "messages" -> {
                            navController.navigate(Screen.Messages.route)
                        }
                        "offers" -> {
                            navController.navigate(Screen.Offers.route)
                        }
                        "profile" -> {
                            navController.navigate(Screen.Profile.route)
                        }
                    }
                },
                isLoggedIn = true
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FA))
                .padding(paddingValues)
        ) {
            when (currentBottomRoute) {
                "home" -> {
                    if (storeType == "grocery") {
                        GroceryHomeScreen(selectedTabIndex)
                    } else {
                        OnlineStoreHomeScreen(selectedTabIndex)
                    }
                }
                else -> {
                    if (storeType == "grocery") {
                        GroceryHomeScreen(selectedTabIndex)
                    } else {
                        OnlineStoreHomeScreen(selectedTabIndex)
                    }
                }
            }
        }
    }
}