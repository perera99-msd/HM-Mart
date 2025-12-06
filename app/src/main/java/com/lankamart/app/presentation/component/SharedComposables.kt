package com.lankamart.app.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lankamart.app.presentation.theme.*

@Composable
fun CategoryGrid() {
    val categories = listOf(
        "Electronics" to Icons.Default.Devices,
        "Fashion" to Icons.Default.ShoppingBag,
        "Home" to Icons.Default.Home,
        "Beauty" to Icons.Default.Face
    )

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(categories) { (name, icon) ->
            CategoryItem(name = name, icon = icon)
        }
    }
}

@Composable
fun CategoryItem(name: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Card(
        modifier = Modifier.size(100.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = OffWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = name,
                tint = DarkTeal,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = TextBlack
            )
        }
    }
}

@Composable
fun FlashSaleSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "⚡ FLASH SALE",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF5722)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Up to 70% OFF",
                    fontSize = 16.sp,
                    color = TextBlack
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { /* Handle flash sale */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF5722),
                        contentColor = Color.White
                    )
                ) {
                    Text("Shop Now")
                }
            }
        }
    }
}

@Composable
fun DailyDealsSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = LightCream)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🔥 DAILY DEALS",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkTeal
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Limited time offers",
                    fontSize = 14.sp,
                    color = TextBlack
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { /* Handle daily deals */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SageGreen,
                        contentColor = Color.White
                    )
                ) {
                    Text("View All Deals")
                }
            }
        }
    }
}

@Composable
fun ProductGrid(isGrocery: Boolean) {
    val products = if (isGrocery) {
        listOf("Apples", "Bananas", "Milk", "Bread", "Eggs")
    } else {
        listOf("Smartphone", "Headphones", "Laptop", "Watch", "Speaker")
    }

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(products) { product ->
            ProductCard(product = product, isGrocery = isGrocery)
        }
    }
}

@Composable
fun ProductCard(product: String, isGrocery: Boolean) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(200.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(
                        if (isGrocery) SageGreen.copy(alpha = 0.2f)
                        else WarmBeige.copy(alpha = 0.2f),
                        RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = product.first().toString(),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isGrocery) SageGreen else WarmBeige
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = product,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = TextBlack
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (isGrocery) "Fresh produce" else "Premium quality",
                fontSize = 12.sp,
                color = TextLightGrey
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (isGrocery) "$4.99" else "$299.99",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = DarkTeal
            )
        }
    }
}