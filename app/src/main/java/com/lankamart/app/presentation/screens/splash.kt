package com.lankamart.app.presentation.screens.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lankamart.app.R
import com.lankamart.app.presentation.navigation.Screen
import com.lankamart.app.presentation.theme.DarkTeal
import com.lankamart.app.presentation.theme.SageGreen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    val alphaAnim = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        alphaAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(1500)
        )
        delay(2000)

        // Navigate to Home Choice Screen
        navController.navigate(Screen.HomeChoice.route) {
            popUpTo(Screen.Splash.route) { inclusive = true }
        }
    }

    // Gradient background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(DarkTeal, Color(0xFF003D3A))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.mipmap.ic_hm_mart),
                contentDescription = "HM MART Logo",
                modifier = Modifier
                    .size(160.dp)
                    .alpha(alphaAnim.value),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "HM MART",
                color = Color.White,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.alpha(alphaAnim.value)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Your Premium Shopping Destination",
                color = SageGreen,
                fontSize = 14.sp,
                modifier = Modifier.alpha(alphaAnim.value)
            )
        }
    }
}