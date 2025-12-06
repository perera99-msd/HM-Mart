package com.lankamart.app.presentation.screens.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lankamart.app.presentation.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesScreen(navController: NavController) {
    Scaffold(
        containerColor = Color(0xFFF8F9FA),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Notifications", fontFamily = Poppins, fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFFF8F9FA)),
                actions = {
                    TextButton(onClick = {}) {
                        Text("Read all", color = DarkTeal, fontFamily = Poppins, fontSize = 12.sp)
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Text(
                    "Today",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 12.dp, start = 8.dp)
                )
            }
            items(3) { index ->
                NotificationItem(index, isToday = true)
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "Earlier",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 12.dp, start = 8.dp)
                )
            }
            items(2) { index ->
                NotificationItem(index, isToday = false)
            }
        }
    }
}

@Composable
fun NotificationItem(index: Int, isToday: Boolean) {
    val isUnread = isToday && index == 0

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .clickable { },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = if(isUnread) 4.dp else 0.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(if(isUnread) DarkTeal.copy(alpha = 0.1f) else Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Notifications,
                    null,
                    tint = if(isUnread) DarkTeal else Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if(index % 2 == 0) "Order Shipped!" else "New Offer Available",
                        fontFamily = Poppins,
                        fontWeight = if(isUnread) FontWeight.Bold else FontWeight.SemiBold,
                        fontSize = 15.sp,
                        color = TextBlack
                    )
                    Text(
                        text = if(isToday) "2h ago" else "2 days ago",
                        fontFamily = Poppins,
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Your order #12345 has been shipped successfully. Track your package now.",
                    fontFamily = Poppins,
                    fontSize = 13.sp,
                    color = if(isUnread) TextDarkGrey else Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if(isUnread) {
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(DarkTeal, CircleShape)
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}