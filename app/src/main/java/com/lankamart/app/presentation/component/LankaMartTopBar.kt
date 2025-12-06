package com.lankamart.app.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lankamart.app.presentation.theme.DarkTeal
import com.lankamart.app.presentation.theme.Poppins
import com.lankamart.app.presentation.theme.SageGreen
import com.lankamart.app.presentation.theme.WarmBeige

@Composable
fun LankaMartTopBar(
    storeType: String,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    onCartClicked: () -> Unit,
    onNotificationsClicked: () -> Unit
) {
    Surface(
        color = Color.White,
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
    ) {
        // FIX: Added windowInsetsPadding for statusBars
        Column(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(top = 8.dp, bottom = 8.dp)
        ) {
            // Top Row: Logo & Actions
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if(storeType == "grocery") "GROCERY" else "ONLINE MALL",
                        fontSize = 12.sp,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        color = if(storeType == "grocery") SageGreen else WarmBeige,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "HM MART",
                        fontSize = 22.sp,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.ExtraBold,
                        color = DarkTeal
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IconButton(
                        onClick = onNotificationsClicked,
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color(0xFFF5F5F5), CircleShape)
                    ) {
                        Icon(Icons.Default.Notifications, null, tint = DarkTeal, modifier = Modifier.size(20.dp))
                    }
                    IconButton(
                        onClick = onCartClicked,
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color(0xFFF5F5F5), CircleShape)
                    ) {
                        Icon(Icons.Default.ShoppingCart, null, tint = DarkTeal, modifier = Modifier.size(20.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Search Bar (Visual Only)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF0F2F5))
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Search, null, tint = Color.Gray)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Search products...", color = Color.Gray, fontFamily = Poppins, fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Custom Tabs
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.Transparent,
                contentColor = DarkTeal,
                edgePadding = 20.dp,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = DarkTeal,
                        height = 3.dp
                    )
                },
                divider = {}
            ) {
                listOf("All", "Fresh", "Deals", "Beverages", "Bakery").forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { onTabSelected(index) },
                        text = {
                            Text(
                                text = title,
                                fontFamily = Poppins,
                                fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 14.sp
                            )
                        }
                    )
                }
            }
        }
    }
}