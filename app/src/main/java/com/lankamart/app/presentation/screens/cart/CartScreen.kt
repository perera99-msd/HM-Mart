package com.lankamart.app.presentation.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lankamart.app.presentation.navigation.Screen
import com.lankamart.app.presentation.theme.DarkTeal
import com.lankamart.app.presentation.theme.Poppins
import com.lankamart.app.presentation.theme.SageGreen
import com.lankamart.app.presentation.theme.TextBlack
import com.lankamart.app.presentation.theme.TextLightGrey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController) {
    // Simulate Login State (Replace with actual Auth logic)
    val isLoggedIn by remember { mutableStateOf(true) }

    Scaffold(
        containerColor = Color(0xFFF8F9FA),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "My Cart",
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = TextBlack)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFFF8F9FA))
            )
        },
        // FIXED: Checkout Section is now a BottomBar to prevent cutoff
        bottomBar = {
            if (isLoggedIn) {
                CartSummaryFooter()
            }
        }
    ) { paddingValues ->
        if (!isLoggedIn) {
            EmptyCartState(navController, paddingValues)
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp), // Extra padding for scroll
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Cart Header
                item {
                    Padding(horizontal = 24.dp) {
                        Text(
                            "3 items picked",
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Medium,
                            color = DarkTeal,
                            fontSize = 14.sp
                        )
                    }
                }

                // Cart Items
                items(3) { index ->
                    CartItemCard(index)
                }
            }
        }
    }
}

@Composable
fun Padding(horizontal: androidx.compose.ui.unit.Dp, content: @Composable () -> Unit) {
    Box(modifier = Modifier.padding(horizontal = horizontal)) {
        content()
    }
}

@Composable
fun CartItemCard(index: Int) {
    var quantity by remember { mutableIntStateOf(1) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image Placeholder
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center
            ) {
                // In real app: Image()
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            when(index) {
                                0 -> Color(0xFFFFEBEE)
                                1 -> Color(0xFFE8F5E9)
                                else -> Color(0xFFE3F2FD)
                            },
                            RoundedCornerShape(8.dp)
                        )
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = when(index) { 0 -> "Fresh Apples"; 1 -> "Organic Avocados"; else -> "Low Fat Milk" },
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = TextBlack
                )
                Text(
                    text = when(index) { 0 -> "1kg"; 1 -> "500g"; else -> "1L" },
                    fontFamily = Poppins,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = when(index) { 0 -> "$4.50"; 1 -> "$6.00"; else -> "$2.99" },
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = DarkTeal
                )
            }

            // Quantity Control
            Column(horizontalAlignment = Alignment.End) {
                IconButton(onClick = { /* Remove */ }) {
                    Icon(Icons.Filled.DeleteOutline, null, tint = Color(0xFFFF5252), modifier = Modifier.size(20.dp))
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Stepper
                Container(
                    modifier = Modifier
                        .height(36.dp)
                        .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(50))
                        .clip(RoundedCornerShape(50))
                        .background(Color(0xFFFAFAFA))
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clickable { if (quantity > 1) quantity-- },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Filled.Remove, null, modifier = Modifier.size(14.dp), tint = Color.Gray)
                        }

                        Text(
                            text = quantity.toString(),
                            fontFamily = Poppins,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )

                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clickable { quantity++ },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Filled.Add, null, modifier = Modifier.size(14.dp), tint = DarkTeal)
                        }
                    }
                }
            }
        }
    }
}

// Reusable Container for custom shapes if needed, or just Box
@Composable
fun Container(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(modifier = modifier) { content() }
}

@Composable
fun CartSummaryFooter() {
    Surface(
        color = Color.White,
        shadowElevation = 24.dp,
        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                // Add navigation bar padding for system gestures
                .windowInsetsPadding(WindowInsets.navigationBars)
        ) {
            // Totals
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Subtotal", fontFamily = Poppins, color = Color.Gray, fontSize = 14.sp)
                Text("$13.49", fontFamily = Poppins, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Delivery", fontFamily = Poppins, color = Color.Gray, fontSize = 14.sp)
                Text("$2.00", fontFamily = Poppins, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }

            PaddingValues(vertical = 16.dp).let {
                HorizontalDivider(color = Color(0xFFF0F0F0))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Total Price", fontFamily = Poppins, color = Color.Gray, fontSize = 12.sp)
                    Text(
                        "$15.49",
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = TextBlack
                    )
                }

                Button(
                    onClick = { /* Checkout */ },
                    modifier = Modifier
                        .height(54.dp)
                        .width(180.dp)
                        .shadow(8.dp, RoundedCornerShape(16.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkTeal),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Checkout", fontFamily = Poppins, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun EmptyCartState(navController: NavController, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(160.dp)
                .background(Color(0xFFF5F5F5), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.ShoppingCart,
                contentDescription = null,
                tint = Color(0xFFE0E0E0),
                modifier = Modifier.size(80.dp)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            "Your cart is empty",
            fontFamily = Poppins,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TextBlack
        )
        Text(
            "Login to see your saved items or start shopping now.",
            fontFamily = Poppins,
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
        )

        Button(
            onClick = { navController.navigate(Screen.Login.route) },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkTeal)
        ) {
            Text("Login Now", fontFamily = Poppins, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = { navController.popBackStack() }) {
            Text("Continue Shopping", color = SageGreen, fontFamily = Poppins, fontWeight = FontWeight.SemiBold)
        }
    }
}