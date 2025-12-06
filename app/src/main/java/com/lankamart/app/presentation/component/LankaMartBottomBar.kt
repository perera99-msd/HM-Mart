package com.lankamart.app.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lankamart.app.presentation.theme.DarkTeal
import com.lankamart.app.presentation.theme.Poppins

@Composable
fun LankaMartBottomBar(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    isLoggedIn: Boolean
) {
    Surface(
        color = Color.White,
        shadowElevation = 10.dp, // Subtle shadow
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars) // Prevents overlap with system nav
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp) // Fixed height for a thinner, premium look
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                icon = if(currentRoute == "home") Icons.Filled.Home else Icons.Outlined.Home,
                label = "Home",
                isSelected = currentRoute == "home",
                onClick = { onNavigate("home") }
            )
            BottomNavItem(
                icon = if(currentRoute == "offers") Icons.Filled.LocalOffer else Icons.Outlined.LocalOffer,
                label = "Offers",
                isSelected = currentRoute == "offers",
                onClick = { onNavigate("offers") }
            )

            // Center Cart Button - INLINE (Same Level)
            Box(
                modifier = Modifier
                    .size(48.dp) // Compact circle
                    .clip(CircleShape)
                    .background(DarkTeal)
                    .clickable { onNavigate("cart") },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = "Cart",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            BottomNavItem(
                icon = if(currentRoute == "messages") Icons.Filled.Mail else Icons.Outlined.Mail,
                label = "Inbox",
                isSelected = currentRoute == "messages",
                onClick = { onNavigate("messages") }
            )
            BottomNavItem(
                icon = if(currentRoute == "profile") Icons.Filled.Person else Icons.Outlined.Person,
                label = "Profile",
                isSelected = currentRoute == "profile",
                onClick = { onNavigate("profile") }
            )
        }
    }
}

@Composable
fun BottomNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) DarkTeal else Color.Gray,
            modifier = Modifier.size(24.dp) // Slightly smaller icon
        )
        // Only show text if selected to keep it clean and thin
        if (isSelected) {
            Text(
                text = label,
                fontSize = 10.sp,
                fontFamily = Poppins,
                color = DarkTeal,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}