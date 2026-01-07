package com.lankamart.app.presentation.screens.signup

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.ArrowDropDown
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
import com.lankamart.app.data.remote.ApiClient
import com.lankamart.app.data.remote.SignupRequest
import com.lankamart.app.presentation.navigation.Screen
import com.lankamart.app.presentation.theme.DarkTeal
import com.lankamart.app.presentation.theme.Poppins
import com.lankamart.app.presentation.utils.systemUIPadding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(navController: NavController) {

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // --- State Variables ---
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var countryCode by remember { mutableStateOf("+94") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmVisible by remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val countryList = listOf("+94", "+91", "+1", "+44", "+61")

    fun isStrongPassword(pwd: String): Boolean {
        val regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#\$%^&+=!]).{8,}$")
        return regex.matches(pwd)
    }

    fun isValidEmail(email: String): Boolean {
        val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return emailPattern.matches(email.trim())
    }

    fun isValidPhone(phone: String): Boolean {
        // Remove all non-digit characters and check length
        val digitsOnly = phone.replace(Regex("[^0-9]"), "")
        return digitsOnly.length >= 9
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .systemUIPadding()
    ) {
        // --- 1. Header ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))
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
                            radius = 500f
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .padding(top = 30.dp, start = 24.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_hm_mart),
                        contentDescription = "HM Mart Logo",
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "HM MART",
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        letterSpacing = 1.sp
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Create Account",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    color = Color.White
                )
            }
        }

        // --- 2. Main Form Content ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 110.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    // Name
                    SignupTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = "Full Name",
                        icon = Icons.Default.Person
                    )
                    Spacer(Modifier.height(8.dp))

                    // Email
                    SignupTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email Address",
                        icon = Icons.Default.Email,
                        keyboardType = KeyboardType.Email
                    )
                    Spacer(Modifier.height(8.dp))

                    // Phone Section
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        // Country Code
                        var expanded by remember { mutableStateOf(false) }
                        Box(
                            modifier = Modifier
                                .weight(0.3f)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(12.dp))
                                .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(12.dp))
                                .clickable { expanded = true }
                                .padding(horizontal = 8.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = countryCode,
                                    fontFamily = Poppins,
                                    fontSize = 14.sp,
                                    color = Color.Black
                                )
                                Icon(Icons.Outlined.ArrowDropDown, null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                            }
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier.background(Color.White)
                            ) {
                                countryList.forEach { code ->
                                    DropdownMenuItem(
                                        text = { Text(code) },
                                        onClick = {
                                            countryCode = code
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(Modifier.width(8.dp))

                        // Phone Number
                        SignupTextField(
                            value = phone,
                            onValueChange = { phone = it },
                            label = "Mobile",
                            icon = Icons.Default.Phone,
                            keyboardType = KeyboardType.Number,
                            modifier = Modifier.weight(0.7f)
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    // Password
                    SignupTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "Password",
                        icon = Icons.Default.Lock,
                        keyboardType = KeyboardType.Password,
                        isPassword = true,
                        passwordVisible = passwordVisible,
                        onVisibilityChange = { passwordVisible = it }
                    )

                    if (password.isNotEmpty() && !isStrongPassword(password)) {
                        Text(
                            "Weak Password: Use 8+ chars, A-Z, 0-9 & symbols.",
                            color = Color.Red.copy(alpha = 0.8f),
                            fontSize = 10.sp,
                            modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    // Confirm Password
                    SignupTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = "Confirm",
                        icon = Icons.Default.Lock,
                        keyboardType = KeyboardType.Password,
                        isPassword = true,
                        passwordVisible = confirmVisible,
                        onVisibilityChange = { confirmVisible = it }
                    )

                    Spacer(Modifier.height(16.dp))

                    // Error Messages
                    if (errorMessage.isNotEmpty()) {
                        Text(
                            text = errorMessage,
                            color = Color.Red,
                            fontSize = 13.sp,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(Modifier.height(8.dp))
                    }

                    // --- SIGN UP BUTTON ---
                    Button(
                        onClick = {
                            errorMessage = ""

                            // Trim inputs
                            val trimmedName = name.trim()
                            val trimmedEmail = email.trim()
                            val trimmedPhone = phone.trim()
                            val trimmedPassword = password.trim()
                            val trimmedConfirmPassword = confirmPassword.trim()

                            // 1. Validation
                            if (trimmedName.isEmpty()) {
                                errorMessage = "Please enter your full name"
                                return@Button
                            }
                            if (trimmedEmail.isEmpty()) {
                                errorMessage = "Please enter your email"
                                return@Button
                            } else if (!isValidEmail(trimmedEmail)) {
                                errorMessage = "Please enter a valid email address"
                                return@Button
                            }
                            if (trimmedPhone.isEmpty()) {
                                errorMessage = "Please enter your phone number"
                                return@Button
                            } else if (!isValidPhone(trimmedPhone)) {
                                val digitsOnly = trimmedPhone.replace(Regex("[^0-9]"), "")
                                errorMessage = "Phone number must have at least 9 digits (entered ${digitsOnly.length})"
                                return@Button
                            }
                            if (trimmedPassword.isEmpty()) {
                                errorMessage = "Please enter a password"
                                return@Button
                            }
                            if (trimmedConfirmPassword.isEmpty()) {
                                errorMessage = "Please confirm your password"
                                return@Button
                            }
                            if (!isStrongPassword(trimmedPassword)) {
                                errorMessage = "Please use a strong password!"
                                return@Button
                            }
                            if (trimmedPassword != trimmedConfirmPassword) {
                                errorMessage = "Passwords do not match!"
                                return@Button
                            }

                            // 2. API Call
                            loading = true
                            scope.launch(Dispatchers.IO) {
                                try {
                                    val cleanPhone = trimmedPhone.replace(Regex("[^0-9]"), "")
                                    
                                    val response = ApiClient.signupApi.registerUser(
                                        SignupRequest(
                                            full_name = trimmedName,
                                            email = trimmedEmail,
                                            country_code = countryCode,
                                            phone = cleanPhone,
                                            password = trimmedPassword
                                        )
                                    ).execute()

                                    launch(Dispatchers.Main) {
                                        loading = false
                                        val body = response.body()
                                        if (body != null) {
                                            if (body.status == "success") {
                                                // Clear form
                                                name = ""
                                                email = ""
                                                phone = ""
                                                password = ""
                                                confirmPassword = ""
                                                
                                                navController.navigate(Screen.Login.route) {
                                                    popUpTo(Screen.Signup.route) { inclusive = true }
                                                }
                                            } else {
                                                errorMessage = body.message
                                            }
                                        } else {
                                            errorMessage = "Server returned empty response"
                                        }
                                    }
                                } catch (e: Exception) {
                                    launch(Dispatchers.Main) {
                                        loading = false
                                        errorMessage = "Unable to connect to server!"
                                        Log.e("SignupError", e.message.toString())
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = DarkTeal)
                    ) {
                        if (loading) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                        else Text("CREATE ACCOUNT", fontFamily = Poppins, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // --- Google Login ---
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CompactSocialButton(
                            text = "Continue with Google",
                            iconRes = R.drawable.ic_google,
                            modifier = Modifier.fillMaxWidth(0.8f)
                        ) {
                            val googleUrl = "https://yourdomain.com/google_login.php"
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(googleUrl)))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Footer
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Already a member? ",
                    color = Color.Gray,
                    fontFamily = Poppins,
                    fontSize = 13.sp
                )
                Text(
                    "Sign In",
                    color = DarkTeal,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    modifier = Modifier.clickable { navController.navigate(Screen.Login.route) }
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun SignupTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onVisibilityChange: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        label = { Text(label, fontSize = 13.sp) },
        leadingIcon = { Icon(icon, null, tint = DarkTeal, modifier = Modifier.size(20.dp)) },
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = { onVisibilityChange(!passwordVisible) }) {
                    Icon(
                        if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        null,
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        },
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = DarkTeal,
            unfocusedBorderColor = Color(0xFFE0E0E0),
            focusedLabelColor = DarkTeal,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        textStyle = androidx.compose.ui.text.TextStyle(fontSize = 14.sp)
    )
}

@Composable
fun CompactSocialButton(
    text: String,
    iconRes: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEEEEEE)),
        shadowElevation = 2.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = text,
                fontFamily = Poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }
}