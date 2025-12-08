package com.lankamart.app.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

data class SignupRequest(
    val name: String,
    val email: String,
    val country_code: String,
    val phone: String,
    val password: String
)

data class SignupResponse(
    val status: String,
    val message: String
)

interface SignupApi {

    @POST("register_user.php")
    suspend fun registerUser(@Body request: SignupRequest): SignupResponse

}
