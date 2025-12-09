package com.lankamart.app.data.remote

data class OAuthRequest(
    val name: String,
    val email: String,
    val google_id: String? = null,
    val facebook_id: String? = null,
    val profile_pic: String? = null
)
