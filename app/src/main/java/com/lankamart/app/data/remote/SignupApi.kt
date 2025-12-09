package com.lankamart.app.data.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignupApi {
    @POST("register_user.php")
    fun registerUser(@Body request: SignupRequest): Call<ApiResponse>

    @POST("social_login.php")
    fun socialLogin(@Body request: SocialLoginRequest): Call<ApiResponse>
}
