package com.lankamart.app.presentation.screens.homechoice

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lankamart.app.presentation.navigation.Screen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.ShoppingCart

@Composable
fun HomeChoiceScreen(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF00BCD4), Color(0xFF006064))
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "Welcome to LankaMart",
                fontSize = 28.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(40.dp))

            ButtonCard(
                title = "Grocery Store",
                icon = Icons.Default.Storefront,
                gradient = Brush.horizontalGradient(
                    listOf(Color(0xFF4CAF50), Color(0xFF2E7D32))
                ),
                onClick = {
                    navController.navigate(Screen.MainHome.route)
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            ButtonCard(
                title = "Online Store",
                icon = Icons.Default.ShoppingCart,
                gradient = Brush.horizontalGradient(
                    listOf(Color(0xFFFF9800), Color(0xFFF57C00))
                ),
                onClick = {
                    navController.navigate(Screen.MainHome.route)
                }
            )
        }
    }
}

@Composable
fun ButtonCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    gradient: Brush,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .background(brush = gradient, shape = RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(20.dp),
        contentAlignment = Alignment.CenterStart
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.width(20.dp))

            Text(
                text = title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}
