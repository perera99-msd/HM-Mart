package com.lankamart.app.presentation.screens.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
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
import com.lankamart.app.presentation.theme.Poppins
import com.lankamart.app.presentation.theme.WarmBeige
import com.lankamart.app.presentation.utils.systemUIPadding
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    // 1. Entrance Animation States
    val transitionState = remember { MutableTransitionState(false) }
    transitionState.targetState = true
    val transition = updateTransition(transitionState, label = "SplashEntrance")

    val alphaAnim by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 1000) },
        label = "Alpha"
    ) { state -> if (state) 1f else 0f }

    val scaleAnim by transition.animateFloat(
        transitionSpec = {
            spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        },
        label = "Scale"
    ) { state -> if (state) 1f else 0.8f }

    val slideUpAnim by transition.animateDp(
        transitionSpec = { tween(durationMillis = 1000, delayMillis = 300) },
        label = "SlideUp"
    ) { state -> if (state) 0.dp else 50.dp }

    val progressAnim = remember { Animatable(0f) }

    // 2. Navigation Logic
    LaunchedEffect(Unit) {
        progressAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 2000, easing = LinearEasing)
        )
        delay(500)

        navController.navigate(Screen.Login.route) {
            popUpTo(Screen.Splash.route) { inclusive = true }
        }
    }

    // 3. UI Content
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(DarkTeal, Color(0xFF003D3A))
                )
            )
            .systemUIPadding(),
        contentAlignment = Alignment.Center
    ) {
        // Decorative Background
        Box(
            modifier = Modifier
                .size(400.dp)
                .offset(y = (-150).dp)
                .alpha(0.05f)
                .background(Color.White, CircleShape)
        )
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(x = 100.dp, y = 200.dp)
                .alpha(0.03f)
                .background(Color.White, CircleShape)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.offset(y = (-20).dp)
        ) {
            Surface(
                modifier = Modifier
                    .size(160.dp)
                    .scale(scaleAnim)
                    .alpha(alphaAnim),
                shape = RoundedCornerShape(32.dp),
                shadowElevation = 12.dp,
                color = Color.White
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_hm_mart),
                        contentDescription = "HM Mart Logo",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier
                    .offset(y = slideUpAnim)
                    .alpha(alphaAnim),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "HM MART",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    letterSpacing = 2.sp,
                    color = WarmBeige
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Premium Shopping Experience",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f),
                    letterSpacing = 0.5.sp
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 60.dp)
                .alpha(alphaAnim)
        ) {
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color.White.copy(alpha = 0.15f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progressAnim.value)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(2.dp))
                        .background(WarmBeige)
                )
            }
        }
    }
}