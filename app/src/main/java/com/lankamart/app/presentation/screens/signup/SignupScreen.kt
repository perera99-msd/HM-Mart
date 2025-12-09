package com.lankamart.app.presentation.screens.signup

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lankamart.app.data.remote.ApiClient
import com.lankamart.app.data.remote.SignupRequest
import com.lankamart.app.presentation.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(navController: NavController) {

    val context = LocalContext.current

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .background(Color.White)
            .padding(top = 40.dp)
    ) {
        Text("Create Account", fontSize = 28.sp, color = Color.Black)
        Spacer(Modifier.height(20.dp))

        // Form Fields
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Full Name") }
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(Modifier.height(12.dp))

        Row {
            var expanded by remember { mutableStateOf(false) }
            Box {
                OutlinedButton(
                    onClick = { expanded = true },
                    modifier = Modifier.width(100.dp)
                ) { Text(countryCode) }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
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
            Spacer(Modifier.width(10.dp))
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                modifier = Modifier.weight(1f),
                label = { Text("Contact Number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Password") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Text(if (passwordVisible) "Hide" else "Show")
                }
            }
        )

        if (password.isNotEmpty() && !isStrongPassword(password)) {
            Text(
                "Password must be at least 8 characters, with uppercase, lowercase, number & symbol.",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Confirm Password") },
            visualTransformation = if (confirmVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { confirmVisible = !confirmVisible }) {
                    Text(if (confirmVisible) "Hide" else "Show")
                }
            }
        )

        Spacer(Modifier.height(20.dp))

        if (errorMessage.isNotEmpty()) {
            Text(errorMessage, color = Color.Red)
            Spacer(Modifier.height(10.dp))
        }

        // Signup Button
        Button(
            onClick = {
                if (!isStrongPassword(password)) {
                    errorMessage = "Please use a strong password!"
                    return@Button
                }
                if (password != confirmPassword) {
                    errorMessage = "Passwords do not match!"
                    return@Button
                }

                loading = true
                errorMessage = ""

                scope.launch(Dispatchers.IO) {
                    try {
                        val response = ApiClient.signupApi.registerUser(
                            SignupRequest(
                                name = name,
                                email = email,
                                country_code = countryCode,
                                phone = phone,
                                password = password
                            )
                        ).execute()

                        launch(Dispatchers.Main) {
                            loading = false
                            val body = response.body()
                            if (body != null) {
                                if (body.status == "success") {
                                    navController.navigate(Screen.Login.route) {
                                        popUpTo(Screen.Signup.route) { inclusive = true }
                                    }
                                } else {
                                    errorMessage = body.message
                                }
                            } else errorMessage = "Server returned empty response"
                        }
                    } catch (e: Exception) {
                        launch(Dispatchers.Main) {
                            loading = false
                            errorMessage = "Unable to connect to server!"
                        }
                        Log.e("SignupError", e.message.toString())
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        ) {
            if (loading) CircularProgressIndicator(color = Color.White)
            else Text("SIGN UP")
        }

        Spacer(Modifier.height(20.dp))

        // Google Login via Web URL
        Button(
            onClick = {
                val googleUrl = "https://yourdomain.com/google_login.php"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(googleUrl))
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(Color(0xFF4285F4)),
            modifier = Modifier.fillMaxWidth()
        ) { Text("Sign up with Google", color = Color.White) }

        Spacer(Modifier.height(12.dp))

        // Facebook Login via Web URL
        Button(
            onClick = {
                val fbUrl = "https://yourdomain.com/facebook_login.php"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(fbUrl))
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(Color(0xFF1877F2)),
            modifier = Modifier.fillMaxWidth()
        ) { Text("Sign up with Facebook", color = Color.White) }
    }
}
