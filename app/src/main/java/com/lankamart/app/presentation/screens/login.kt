package com.lankamart.app.presentation.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lankamart.app.R
import com.lankamart.app.presentation.navigation.Screen
import com.lankamart.app.presentation.theme.DarkTeal
import com.lankamart.app.presentation.theme.Poppins
import com.lankamart.app.presentation.theme.SageGreen
import com.lankamart.app.presentation.utils.systemUIPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var loginMethod by remember { mutableStateOf("email") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .systemUIPadding()
    ) {
        // --- 1. Premium Header ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .clip(RoundedCornerShape(bottomStart = 60.dp, bottomEnd = 60.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(DarkTeal, Color(0xFF004D40))
                    )
                )
        ) {
            // Subtle Radial Glow
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            colors = listOf(Color.White.copy(alpha = 0.05f), Color.Transparent),
                            radius = 600f
                        )
                    )
            )

            // Header Content
            Column(
                modifier = Modifier
                    .padding(top = 50.dp, start = 28.dp),
                verticalArrangement = Arrangement.Center
            ) {
                // Logo Row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color.White,
                        modifier = Modifier.size(48.dp),
                        shadowElevation = 6.dp
                    ) {
                        Box(modifier = Modifier.padding(8.dp)) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_hm_mart),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Text(
                        text = "HM MART",
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.White,
                        letterSpacing = 1.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Welcome Back",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    color = Color.White
                )
                Text(
                    text = "Sign in to your premium account",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }

        // --- 2. Main Content ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- LOGIN CARD ---
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Method Toggle
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFF5F5F5))
                            .padding(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(10.dp))
                                .background(Brush.horizontalGradient(listOf(DarkTeal, SageGreen)))
                                .align(if (loginMethod == "email") Alignment.CenterStart else Alignment.CenterEnd)
                        )
                        Row(modifier = Modifier.fillMaxSize()) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .clickable { loginMethod = "email" },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "Email",
                                    fontFamily = Poppins,
                                    fontWeight = if (loginMethod == "email") FontWeight.Bold else FontWeight.Medium,
                                    fontSize = 14.sp,
                                    color = if (loginMethod == "email") Color.White else Color.Gray
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .clickable { loginMethod = "phone" },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "Phone",
                                    fontFamily = Poppins,
                                    fontWeight = if (loginMethod == "phone") FontWeight.Bold else FontWeight.Medium,
                                    fontSize = 14.sp,
                                    color = if (loginMethod == "phone") Color.White else Color.Gray
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Inputs
                    if (loginMethod == "email") {
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Email Address", fontSize = 14.sp) },
                            leadingIcon = { Icon(Icons.Default.Email, null, tint = DarkTeal) },
                            shape = RoundedCornerShape(14.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = DarkTeal,
                                unfocusedBorderColor = Color(0xFFE0E0E0),
                                focusedLabelColor = DarkTeal,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        )
                    } else {
                        OutlinedTextField(
                            value = phone,
                            onValueChange = {
                                // Only allow digits, +, and spaces
                                if (it.length <= 15 && it.all { char ->
                                        char.isDigit() || char == '+' || char == ' ' || char == '(' || char == ')' || char == '-'
                                    }) {
                                    phone = it
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Phone Number", fontSize = 14.sp) },
                            leadingIcon = { Icon(Icons.Default.Phone, null, tint = DarkTeal) },
                            shape = RoundedCornerShape(14.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = DarkTeal,
                                unfocusedBorderColor = Color(0xFFE0E0E0),
                                focusedLabelColor = DarkTeal,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            placeholder = { Text("+94 77 123 4567", color = Color.Gray.copy(alpha = 0.6f)) }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Password", fontSize = 14.sp) },
                        leadingIcon = { Icon(Icons.Default.Lock, null, tint = DarkTeal) },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    null,
                                    tint = Color.Gray
                                )
                            }
                        },
                        shape = RoundedCornerShape(14.dp),
                        singleLine = true,
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DarkTeal,
                            unfocusedBorderColor = Color(0xFFE0E0E0),
                            focusedLabelColor = DarkTeal,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Forgot Password
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Forgot Password?",
                            color = DarkTeal,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { /* TODO */ }
                                .padding(8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Sign In Button
                    Button(
                        onClick = {
                            navController.navigate(Screen.HomeChoice.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = DarkTeal),
                        shape = RoundedCornerShape(14.dp),
                        elevation = ButtonDefaults.buttonElevation(6.dp)
                    ) {
                        Text(
                            "SIGN IN",
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            letterSpacing = 1.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Socials
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        SocialButton(R.drawable.ic_google, "Google", Modifier.weight(1f))
                        SocialButton(R.drawable.ic_facebook, "Facebook", Modifier.weight(1f))
                    }
                }
            }

            // --- 3. FOOTER SECTION ---
            Spacer(modifier = Modifier.height(32.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Sign Up
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Don't have an account? ",
                        color = Color.Gray,
                        fontFamily = Poppins,
                        fontSize = 14.sp
                    )
                    Text(
                        "Sign Up",
                        color = DarkTeal,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.Signup.route)
                        }
                    )
                }

                // Guest Button
                Surface(
                    onClick = {
                        navController.navigate(Screen.HomeChoice.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
                    shape = RoundedCornerShape(50),
                    color = Color(0xFFE0F2F1)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Continue as Guest",
                            color = DarkTeal,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            Icons.Outlined.ArrowForward,
                            null,
                            tint = DarkTeal,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun SocialButton(iconRes: Int, text: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .height(50.dp)
            .clickable { /* Handle social login */ },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEEEEEE))
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = text,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    color = Color.Black
                )
            }
        }
    }
}