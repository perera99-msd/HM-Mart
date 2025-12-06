package com.lankamart.app.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lankamart.app.presentation.theme.*

@Composable
fun ProfileScreen(navController: NavController) {
    // In a real app, these values come from Auth state
    val isLoggedIn = true

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Color(0xFFF8F9FA)),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        // Header
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            ) {
                // Background
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .clip(RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))
                        .background(DarkTeal)
                )

                // Content
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "My Profile",
                        color = Color.White,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(top = 20.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Avatar
                    Surface(
                        shape = CircleShape,
                        shadowElevation = 10.dp,
                        modifier = Modifier.size(100.dp),
                        color = Color.White
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("JD", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = DarkTeal)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("John Doe", fontFamily = Poppins, fontWeight = FontWeight.Bold, fontSize = 22.sp, color = TextBlack)
                    Text("john.doe@example.com", fontFamily = Poppins, fontSize = 14.sp, color = Color.Gray)
                }
            }
        }

        // Menu Sections
        item {
            Column(modifier = Modifier.padding(24.dp)) {
                Text("Account", fontFamily = Poppins, fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(bottom = 16.dp))

                ProfileItem("My Orders", Icons.Default.ShoppingBag)
                ProfileItem("Shipping Addresses", Icons.Default.LocationOn)
                ProfileItem("Payment Methods", Icons.Default.CreditCard)

                Spacer(modifier = Modifier.height(24.dp))

                Text("Settings", fontFamily = Poppins, fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(bottom = 16.dp))

                ProfileItem("Notifications", Icons.Default.Notifications)
                ProfileItem("Language", Icons.Default.Language)
                ProfileItem("Help Center", Icons.Default.Help)

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE5E5), contentColor = Color.Red),
                    shape = RoundedCornerShape(12.dp),
                    elevation = null
                ) {
                    Text("Log Out", fontFamily = Poppins, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun ProfileItem(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Surface(
        onClick = {},
        color = Color.White,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(DarkTeal.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = DarkTeal, modifier = Modifier.size(20.dp))
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(text, fontFamily = Poppins, fontSize = 15.sp, modifier = Modifier.weight(1f))

            Icon(Icons.Outlined.ChevronRight, null, tint = Color.Gray)
        }
    }
}