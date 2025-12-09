package com.lankamart.app.data.remote

data class SocialLoginRequest(
    val provider: String,
    val token: String,
    val name: String,
    val email: String,
    val country_code: String,
    val phone: String
)
