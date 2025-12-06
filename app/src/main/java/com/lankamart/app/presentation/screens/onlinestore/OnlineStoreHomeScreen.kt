package com.lankamart.app.presentation.screens.onlinestore

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.lankamart.app.presentation.theme.*

@Composable
fun OnlineStoreHomeScreen(selectedTabIndex: Int) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA)),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        // --- 1. Premium Hero Slider ---
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        model = "https://images.unsplash.com/photo-1483985988355-763728e1935b?w=800", // Resized
                        contentDescription = "Fashion",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Premium Dark Overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Brush.horizontalGradient(listOf(Color.Black.copy(alpha=0.7f), Color.Transparent)))
                    )

                    Column(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(24.dp)
                    ) {
                        Text(
                            "SUMMER\nCOLLECTION",
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 2.sp,
                            lineHeight = 32.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            "Up to 50% Off",
                            color = WarmBeige,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("EXPLORE", color = Color.Black, fontSize = 12.sp, letterSpacing = 1.sp)
                        }
                    }
                }
            }
        }

        // --- 2. Brand Circles ---
        item {
            Text(
                "Top Brands",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val brands = listOf("Nike", "Apple", "Samsung", "Adidas", "Zara")
                items(brands) { name ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = Color.White,
                            shadowElevation = 2.dp,
                            modifier = Modifier.size(70.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(name.take(1), fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.Black)
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(name, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }
        }

        // --- 3. Trending Products ---
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Trending Now", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text("See All", color = DarkTeal, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }
        }

        items(4) { rowIndex ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TechProductCard(
                    name = if(rowIndex%2==0) "Sony Headphone" else "MacBook Air",
                    price = if(rowIndex%2==0) "$299" else "$999",
                    img = if(rowIndex%2==0) "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=400" else "https://images.unsplash.com/photo-1517336714731-489689fd1ca4?w=400",
                    modifier = Modifier.weight(1f)
                )
                TechProductCard(
                    name = if(rowIndex%2==0) "Smart Watch" else "Nike Air Max",
                    price = if(rowIndex%2==0) "$199" else "$129",
                    img = if(rowIndex%2==0) "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=400" else "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun TechProductCard(name: String, price: String, img: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(260.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) {
                AsyncImage(
                    model = img,
                    contentDescription = name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = Color.White.copy(alpha = 0.9f),
                    modifier = Modifier.padding(12.dp).align(Alignment.TopStart)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Star, null, tint = Color(0xFFFFC107), modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("4.8", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(name, fontWeight = FontWeight.Bold, fontSize = 16.sp, maxLines = 1)
                    Text("Electronics", fontSize = 12.sp, color = Color.Gray)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(price, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = DarkTeal)

                    Surface(
                        shape = CircleShape,
                        color = Color.Black,
                        modifier = Modifier.size(32.dp).clickable { }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.ArrowForward, null, tint = Color.White, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }
    }
}