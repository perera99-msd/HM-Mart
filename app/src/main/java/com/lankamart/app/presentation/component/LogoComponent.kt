
package com.lankamart.app.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lankamart.app.R
import com.lankamart.app.presentation.theme.DarkTeal
import com.lankamart.app.presentation.theme.SageGreen
import com.lankamart.app.presentation.theme.WarmBeige

@Composable
fun HMMartLogo(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    showText: Boolean = true,
    logoImage: Painter? = null
) {
    Box(
        modifier = modifier
            .size(size)
    ) {
        if (logoImage != null) {
            // Use uploaded image logo
            Image(
                painter = logoImage,
                contentDescription = "HM MART Logo",
                modifier = Modifier.fillMaxSize()
            )
        } else {
            // Default text logo
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkTeal, RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                ) {
                    Text(
                        text = "HM",
                        color = Color.White,
                        fontSize = (size.value * 0.32).sp,
                        fontWeight = FontWeight.Bold
                    )
                    if (showText) {
                        Text(
                            text = "MART",
                            color = WarmBeige,
                            fontSize = (size.value * 0.18).sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

// Small Logo variant for headers
@Composable
fun HMMartLogoSmall() {
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(DarkTeal, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "HM",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
