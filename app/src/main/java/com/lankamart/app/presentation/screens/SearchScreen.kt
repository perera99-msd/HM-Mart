// SearchScreen.kt
package com.lankamart.app.presentation.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.lankamart.app.presentation.navigation.Screen
import com.lankamart.app.presentation.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    val recentSearches = remember { listOf("Apple", "Milk", "Bread", "Headphones", "Laptop") }
    val popularProducts = remember {
        listOf(
            Product("Fresh Apples", "$4.99", "grocery", "https://images.unsplash.com/photo-1560806887-1e4cd0b6cbd6?w=400"),
            Product("Wireless Headphones", "$299", "electronics", "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=400"),
            Product("Organic Milk", "$3.49", "grocery", "https://images.unsplash.com/photo-1563636619-e9143da7973b?w=400"),
            Product("Running Shoes", "$89.99", "fashion", "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400"),
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp), // Increased slightly for touch target
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
                        shape = RoundedCornerShape(20.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedContainerColor = Color(0xFFF5F5F5),
                            unfocusedContainerColor = Color(0xFFF5F5F5)
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(
                            onSearch = { /* Perform search */ }
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp)
        ) {
            if (searchQuery.isEmpty()) {
                // Recent Searches
                item {
                    Text(
                        "Recent Searches",
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }

                items(recentSearches) { search ->
                    RecentSearchItem(search, onClick = { searchQuery = it })
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        "Popular Products",
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            }

            // Search Results or Popular Products
            items(if (searchQuery.isEmpty()) popularProducts else popularProducts.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }) { product ->
                SearchProductItem(product, navController)
            }
        }
    }
}

@Composable
fun RecentSearchItem(query: String, onClick: (String) -> Unit) {
    Surface(
        onClick = { onClick(query) },
        color = Color(0xFFF5F5F5),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(query, fontFamily = Poppins, color = TextBlack)
            Icon(Icons.Default.History, null, tint = Color.Gray, modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun SearchProductItem(product: Product, navController: NavController) {
    Card(
        onClick = {
            // FIX: Ensure route matches MainActivity definition
            navController.navigate("product_detail/${product.name.hashCode()}")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    product.name,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
                Text(
                    product.category.replaceFirstChar { it.uppercase() },
                    fontFamily = Poppins,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    product.price,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = DarkTeal
                )
            }
        }
    }
}

data class Product(
    val name: String,
    val price: String,
    val category: String,
    val imageUrl: String
)