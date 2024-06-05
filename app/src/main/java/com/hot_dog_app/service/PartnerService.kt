package com.hot_dog_app.service

import com.hot_dog_app.model.LoginRequest
import com.hot_dog_app.model.LoginResponse
import com.hot_dog_app.model.PartnerRequest
import com.hot_dog_app.model.PartnerResponse
import com.hot_dog_app.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface PartnerService {

    @Headers("Content-Type: application/json")
    @POST("login_check")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>?

    @POST("partner")
    fun create(
        @Body request: PartnerRequest
    ): Call<RegisterResponse>?
}