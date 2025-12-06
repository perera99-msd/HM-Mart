package com.lankamart.app.presentation.screens.grocery

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.lankamart.app.presentation.theme.*

@Composable
fun GroceryHomeScreen(selectedTabIndex: Int, navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA)),
        contentPadding = PaddingValues(bottom = 24.dp) // Little extra breathing room at bottom
    ) {
        // --- 1. Immersive Hero Banner ---
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            ) {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1542838132-92c53300491e?q=80&w=800&auto=format&fit=crop",
                    contentDescription = "Fresh Vegetables",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Gradient Overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f))
                            )
                        )
                )

                // Content
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(24.dp)
                ) {
                    Surface(
                        color = SageGreen,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Text(
                            "FRESH ARRIVAL",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Text(
                        "Organic Farm\nVegetables",
                        color = Color.White,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        lineHeight = 34.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Get 40% OFF on your first order",
                        color = Color.White.copy(alpha = 0.9f),
                        fontFamily = Poppins,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { /* Shop Now Logic */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
                    ) {
                        Text("Shop Now", color = TextBlack, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // --- 2. Categories ---
        item {
            SectionHeader("Categories", "See all")
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                val categories = listOf(
                    Triple("Fruits", "https://images.unsplash.com/photo-1619566636858-adf3ef46400b?w=200", Color(0xFFFFEBEE)),
                    Triple("Veg", "https://images.unsplash.com/photo-1566385101042-1a0aa0c1268c?w=200", Color(0xFFE8F5E9)),
                    Triple("Meat", "https://images.unsplash.com/photo-1607623814075-e51df1bdc82f?w=200", Color(0xFFFFEBEE)),
                    Triple("Dairy", "https://images.unsplash.com/photo-1628088062854-d1870b4553da?w=200", Color(0xFFE3F2FD)),
                    Triple("Bakery", "https://images.unsplash.com/photo-1509440159596-0249088772ff?w=200", Color(0xFFFFF3E0))
                )

                items(categories) { (name, img, color) ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape)
                                .background(color)
                                .clickable { },
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = img,
                                contentDescription = name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(name, fontFamily = Poppins, fontWeight = FontWeight.Medium, fontSize = 12.sp)
                    }
                }
            }
        }

        // --- 3. Fresh Picks (Grid) ---
        item {
            Spacer(modifier = Modifier.height(24.dp))
            SectionHeader("Fresh Picks", "")
        }

        items(4) { rowIndex ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                GroceryProductCard(
                    name = if(rowIndex%2==0) "Red Apple" else "Fresh Milk",
                    price = "$4.99",
                    img = if(rowIndex%2==0) "https://images.unsplash.com/photo-1560806887-1e4cd0b6cbd6?w=400" else "https://images.unsplash.com/photo-1563636619-e9143da7973b?w=400",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        navController.navigate("product_detail/grocery_$rowIndex")
                    }
                )
                GroceryProductCard(
                    name = if(rowIndex%2==0) "Broccoli" else "Farm Eggs",
                    price = "$2.49",
                    img = if(rowIndex%2==0) "https://images.unsplash.com/photo-1459411621453-7debff8f8432?w=400" else "https://images.unsplash.com/photo-1516483638261-f4dbaf036963?w=400",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        navController.navigate("product_detail/grocery_item_$rowIndex")
                    }
                )
            }
        }
    }
}

@Composable
fun SectionHeader(title: String, action: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, fontFamily = Poppins, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TextBlack)
        if(action.isNotEmpty()) {
            Text(action, fontFamily = Poppins, fontSize = 13.sp, color = DarkTeal, fontWeight = FontWeight.SemiBold, modifier = Modifier.clickable {})
        }
    }
}

@Composable
fun GroceryProductCard(name: String, price: String, img: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .height(220.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(Color(0xFFF5F5F5))
            ) {
                AsyncImage(
                    model = img,
                    contentDescription = name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                IconButton(
                    onClick = {},
                    modifier = Modifier.align(Alignment.TopEnd).padding(4.dp)
                ) {
                    Icon(Icons.Default.FavoriteBorder, null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                }
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Text(name, fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, maxLines = 1)
                Text("1kg", fontSize = 12.sp, color = Color.Gray)

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(price, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = DarkTeal)
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = SageGreen,
                        modifier = Modifier.size(28.dp).clickable { }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Add, null, tint = Color.White, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }
    }
}