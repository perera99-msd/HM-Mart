package com.lankamart.app.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = DarkTeal,
    onPrimary = PureWhite,
    secondary = SageGreen,
    onSecondary = PureWhite,
    tertiary = WarmBeige,
    background = PureWhite,
    surface = OffWhite,
    onBackground = TextBlack,
    onSurface = TextBlack,
    error = ErrorRed
)

@Composable
fun LankaMartTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // We default to light theme for now as per your request for "Fresh/Clean" look
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme // You can implement DarkColorScheme later

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Set Status bar color to Primary (Dark Teal)
            window.statusBarColor = DarkTeal.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}