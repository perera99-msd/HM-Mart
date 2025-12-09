package com.lankamart.app.presentation.screens.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var loginMethod by remember { mutableStateOf("email") }

    var loading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .systemUIPadding()
    ) {
        // --- Header ---
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
            Column(
                modifier = Modifier.padding(top = 50.dp, start = 28.dp),
                verticalArrangement = Arrangement.Center
            ) {
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

        // --- Main Content ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                    // Login Method Toggle
                    LoginMethodToggle(loginMethod) { loginMethod = it }
                    Spacer(modifier = Modifier.height(24.dp))

                    // Inputs
                    val identifier = if (loginMethod == "email") email else phone
                    LoginTextField(
                        value = identifier,
                        onValueChange = {
                            if (loginMethod == "email") email = it
                            else phone = it.filter { char -> char.isDigit() }
                        },
                        label = if (loginMethod == "email") "Email Address" else "Phone Number",
                        leadingIcon = if (loginMethod == "email") Icons.Default.Email else Icons.Default.Phone,
                        keyboardType = if (loginMethod == "email") KeyboardType.Email else KeyboardType.Number
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LoginTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "Password",
                        leadingIcon = Icons.Default.Lock,
                        keyboardType = KeyboardType.Password,
                        isPassword = true,
                        passwordVisible = passwordVisible,
                        onVisibilityChange = { passwordVisible = it }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Forgot Password
                    Text(
                        text = "Forgot Password?",
                        color = DarkTeal,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        modifier = Modifier
                            .clickable { /* TODO: Forgot Password */ }
                            .padding(8.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // SIGN IN BUTTON
                    Button(
                        onClick = {
                            val idTrim = identifier.trim()
                            val pwTrim = password.trim()

                            if (idTrim.isEmpty() || pwTrim.isEmpty()) {
                                errorMessage = "Please fill all fields"
                                return@Button
                            }

                            scope.launch(Dispatchers.IO) {
                                loading = true
                                errorMessage = ""

                                try {
                                    val url = URL("https://hmmart.cpsharetxt.com/login.php")
                                    val conn = url.openConnection() as HttpURLConnection
                                    conn.requestMethod = "POST"
                                    conn.setRequestProperty("Content-Type", "application/json")
                                    conn.doOutput = true

                                    val json = JSONObject().apply {
                                        put("identifier", idTrim)
                                        put("password", pwTrim)
                                    }

                                    OutputStreamWriter(conn.outputStream).use {
                                        it.write(json.toString())
                                    }

                                    val response = conn.inputStream.bufferedReader().readText()
                                    val obj = JSONObject(response)

                                    launch(Dispatchers.Main) {
                                        loading = false

                                        if (obj.getString("status") == "success") {
                                            val userId = obj.getInt("user_id")
                                            val fullName = obj.getString("full_name")

                                            navController.navigate("home_choice/$userId/$fullName") {
                                                popUpTo("login") { inclusive = true }
                                            }

                                        } else {
                                            errorMessage = obj.getString("message")
                                        }
                                    }

                                } catch (e: Exception) {
                                    launch(Dispatchers.Main) {
                                        loading = false
                                        errorMessage = "Login failed. Please try again."
                                    }
                                    Log.e("LOGIN_ERROR", e.toString())
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = DarkTeal)
                    ) {
                        Text(
                            "SIGN IN",
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    if (loading) {
                        Spacer(modifier = Modifier.height(12.dp))
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    }

                    if (errorMessage.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(errorMessage, color = Color.Red, fontSize = 12.sp)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // --- Social Buttons ---
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        SocialButton(R.drawable.ic_google, "Google") { /* TODO */ }
                        SocialButton(R.drawable.ic_facebook, "Facebook") { /* TODO */ }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            FooterSection(navController)
        }
    }
}

// --- Other Composables (unchanged) ---


// --- Other Composables (LoginMethodToggle, LoginTextField, SocialButton, FooterSection) ---
// Keep the code exactly as in your original file

// --- Login Toggle ---
@Composable
fun LoginMethodToggle(selected: String, onSelect: (String) -> Unit) {
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
                .align(if (selected == "email") Alignment.CenterStart else Alignment.CenterEnd)
        )
        Row(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable { onSelect("email") },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Email",
                    fontFamily = Poppins,
                    fontWeight = if (selected == "email") FontWeight.Bold else FontWeight.Medium,
                    fontSize = 14.sp,
                    color = if (selected == "email") Color.White else Color.Gray
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable { onSelect("phone") },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Phone",
                    fontFamily = Poppins,
                    fontWeight = if (selected == "phone") FontWeight.Bold else FontWeight.Medium,
                    fontSize = 14.sp,
                    color = if (selected == "phone") Color.White else Color.Gray
                )
            }
        }
    }
}

// --- Login TextField ---
@Composable
fun LoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: androidx.compose.ui.graphics.vector.ImageVector,
    keyboardType: KeyboardType,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onVisibilityChange: (Boolean) -> Unit = {},
    placeholder: String? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label, fontSize = 14.sp) },
        leadingIcon = { Icon(leadingIcon, null, tint = DarkTeal) },
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = { onVisibilityChange(!passwordVisible) }) {
                    Icon(
                        if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        null,
                        tint = Color.Gray
                    )
                }
            }
        },
        placeholder = placeholder?.let { { Text(it, color = Color.Gray.copy(alpha = 0.6f)) } },
        shape = RoundedCornerShape(14.dp),
        singleLine = true,
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = DarkTeal,
            unfocusedBorderColor = Color(0xFFE0E0E0),
            focusedLabelColor = DarkTeal,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

// --- Social Buttons ---
@Composable
fun SocialButton(iconRes: Int, text: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .height(50.dp)
            .clickable { onClick() },
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

// --- Footer Section ---
@Composable
fun FooterSection(navController: NavController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
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
                modifier = Modifier.clickable { navController.navigate(Screen.Signup.route) }
            )
        }

        Surface(
            onClick = { navController.navigate(Screen.HomeChoice.route) { popUpTo(Screen.Login.route) { inclusive = true } } },
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
                Icon(Icons.Outlined.ArrowForward, null, tint = DarkTeal, modifier = Modifier.size(16.dp))
            }
        }
    }
}
