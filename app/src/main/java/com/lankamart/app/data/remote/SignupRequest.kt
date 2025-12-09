package com.lankamart.app.data.remote

data class SignupRequest(
    val name: String,
    val email: String,
    val country_code: String,
    val phone: String,
    val password: String
)
