// LankaMartTopBar.kt
package com.lankamart.app.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lankamart.app.presentation.navigation.Screen
import com.lankamart.app.presentation.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LankaMartTopBar(
    storeType: String,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    onCartClicked: () -> Unit,
    onNotificationsClicked: () -> Unit,
    navController: NavController? = null
) {
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .statusBarsPadding() // FIX: Pushes content below the Phone's Status Bar
    ) {
        // Search Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.Gray
                    )
                },
                placeholder = {
                    Text(
                        "Search products...",
                        fontFamily = Poppins,
                        fontSize = 14.sp
                    )
                },
                shape = RoundedCornerShape(25.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = DarkTeal,
                    unfocusedBorderColor = Color(0xFFEEEEEE),
                    focusedContainerColor = Color(0xFFF8F9FA),
                    unfocusedContainerColor = Color(0xFFF8F9FA)
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        navController?.navigate(Screen.Search.route)
                    }
                )
            )

            // Cart Icon with Badge
            Box(
                modifier = Modifier.size(48.dp)
            ) {
                BadgedBox(
                    badge = {
                        Badge(
                            containerColor = Color.Red,
                            modifier = Modifier.size(20.dp).offset(x = (-4).dp, y = 4.dp)
                        ) {
                            Text("3", fontSize = 10.sp, color = Color.White)
                        }
                    }
                ) {
                    IconButton(
                        onClick = { onCartClicked() },
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFF5F5F5))
                    ) {
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = "Cart",
                            tint = DarkTeal
                        )
                    }
                }
            }

            // Notifications Icon
            IconButton(
                onClick = { onNotificationsClicked() },
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF5F5F5))
            ) {
                Icon(
                    Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = DarkTeal
                )
            }
        }

        // Tabs
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.White,
            contentColor = DarkTeal,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    height = 3.dp,
                    color = DarkTeal
                )
            }
        ) {
            val tabs = if (storeType == "grocery") {
                listOf("Home", "Categories", "Deals", "Recipes")
            } else {
                listOf("Home", "Electronics", "Fashion", "Living")
            }

            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { onTabSelected(index) },
                    text = {
                        Text(
                            text = title,
                            fontFamily = Poppins,
                            fontSize = 13.sp,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                            maxLines = 1
                        )
                    },
                    selectedContentColor = DarkTeal,
                    unselectedContentColor = Color.Gray
                )
            }
        }
    }
}