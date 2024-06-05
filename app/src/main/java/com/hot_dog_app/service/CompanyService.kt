package com.hot_dog_app.service

import com.hot_dog_app.model.CompanyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface CompanyService{
    @GET("company")
    fun getAllCompanies(@Header("Authorization") token: String): Call<List<CompanyResponse?>?>?
}