// ProductDetailScreen.kt
package com.lankamart.app.presentation.screens.productdetail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.lankamart.app.presentation.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    navController: NavController,
    productId: String
) {
    var quantity by remember { mutableIntStateOf(1) }
    var selectedSize by remember { mutableStateOf("M") }
    var isFavorite by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            Surface(
                color = Color.White,
                shadowElevation = 24.dp,
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
                modifier = Modifier.navigationBarsPadding() // Avoid overlapping Android Bottom Nav
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Quantity Control
                    Box(
                        modifier = Modifier
                            .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(50))
                            .clip(RoundedCornerShape(50))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.padding(horizontal = 16.dp)
                        ) {
                            IconButton(
                                onClick = { if (quantity > 1) quantity-- },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(Icons.Default.Remove, null, tint = Color.Gray)
                            }
                            Text(
                                text = quantity.toString(),
                                fontFamily = Poppins,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                            IconButton(
                                onClick = { quantity++ },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(Icons.Default.Add, null, tint = DarkTeal)
                            }
                        }
                    }

                    // Add to Cart Button
                    Button(
                        onClick = { navController.navigate("cart") },
                        modifier = Modifier.weight(1f).height(52.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = DarkTeal),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            "Add to Cart",
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        // We do NOT use paddingValues here because we want the image to go BEHIND the status bar (immersive)
        // But we must add padding to the bottom of the content so it doesn't get hidden behind the bottomBar
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = paddingValues.calculateBottomPadding() + 20.dp)
        ) {
            item {
                // Product Image
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .background(Color(0xFFF5F5F5))
                ) {
                    AsyncImage(
                        model = "https://images.unsplash.com/photo-1560769629-975ec94e6a86?w=800",
                        contentDescription = "Product Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Back Button (Safe Area Fix)
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .statusBarsPadding() // Pushes it down below the status bar
                            .align(Alignment.TopStart)
                            .padding(16.dp)
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.9f))
                    ) {
                        Icon(Icons.Default.ArrowBack, null, tint = Color.Black)
                    }

                    // Favorite Button (Safe Area Fix)
                    IconButton(
                        onClick = { isFavorite = !isFavorite },
                        modifier = Modifier
                            .statusBarsPadding() // Pushes it down below the status bar
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.9f))
                    ) {
                        Icon(
                            if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            null,
                            tint = if (isFavorite) Color.Red else Color.Gray
                        )
                    }
                }

                // Product Details
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        "Premium Headphones",
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = TextBlack
                    )

                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Star, null, tint = Color(0xFFFFC107), modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("4.8", fontFamily = Poppins, fontWeight = FontWeight.Medium)
                        Text(" (128 reviews)", color = Color.Gray, fontSize = 14.sp)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "$299.99",
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        color = DarkTeal
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Size Selector (for clothing/fashion)
                    Text(
                        "Size",
                        fontFamily = Poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("XS", "S", "M", "L", "XL").forEach { size ->
                            Surface(
                                onClick = { selectedSize = size },
                                shape = RoundedCornerShape(8.dp),
                                color = if (selectedSize == size) DarkTeal else Color(0xFFF5F5F5),
                                modifier = Modifier.size(40.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        size,
                                        color = if (selectedSize == size) Color.White else Color.Black,
                                        fontFamily = Poppins,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Description
                    Text(
                        "Description",
                        fontFamily = Poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Premium quality headphones with noise cancellation feature. " +
                                "Perfect for music lovers and professionals. Comfortable over-ear design with " +
                                "memory foam ear cushions for extended use.",
                        fontFamily = Poppins,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}