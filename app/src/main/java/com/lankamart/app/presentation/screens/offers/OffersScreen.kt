package com.lankamart.app.presentation.screens.offers

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lankamart.app.presentation.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OffersScreen(navController: NavController) {
    Scaffold(
        containerColor = Color(0xFFF8F9FA),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Exclusive Offers", fontFamily = Poppins, fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFFF8F9FA))
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(4) { index ->
                CouponCard(index)
            }
        }
    }
}

@Composable
fun CouponCard(index: Int) {
    val bgColor = when(index % 3) {
        0 -> Color(0xFFE8F5E9) // Green tint
        1 -> Color(0xFFFFF3E0) // Orange tint
        else -> Color(0xFFE3F2FD) // Blue tint
    }

    val accentColor = when(index % 3) {
        0 -> SageGreen
        1 -> Color(0xFFFF9800)
        else -> DarkTeal
    }

    Card(
        modifier = Modifier.fillMaxWidth().height(160.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            // Left Side (Discount)
            Box(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxHeight()
                    .background(accentColor),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${20 + index * 5}%",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        fontFamily = Poppins
                    )
                    Text(
                        text = "OFF",
                        color = Color.White.copy(alpha = 0.9f),
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        fontFamily = Poppins
                    )
                }
            }

            // Dotted Divider
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .background(Color.White)
            ) {
                androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxHeight()) {
                    drawLine(
                        color = Color(0xFFEEEEEE),
                        start = androidx.compose.ui.geometry.Offset(0f, 0f),
                        end = androidx.compose.ui.geometry.Offset(0f, size.height),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f),
                        strokeWidth = 4f
                    )
                }
            }

            // Right Side (Details)
            Column(
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxHeight()
                    .background(bgColor)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = if(index==0) "Fresh Vegetables" else "Electronics Sale",
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = TextBlack
                    )
                    Text(
                        text = "Valid until 30 Dec 2024",
                        fontFamily = Poppins,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                // Code Copy Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, accentColor.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                        .background(Color.White.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "SAVE${20+index*5}",
                        color = TextBlack,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = Poppins,
                        letterSpacing = 1.sp
                    )
                    Icon(
                        Icons.Default.ContentCopy,
                        contentDescription = "Copy",
                        tint = accentColor,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}