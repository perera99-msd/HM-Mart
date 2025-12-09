package com.lankamart.app.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://hmmart.cpsharetxt.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val signupApi: SignupApi = retrofit.create(SignupApi::class.java)
}
