package com.hot_dog_app.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KClass


class RetrofitAdapter() {

    private val BASE_URL = "http://192.168.13.86:8000/api/"
    fun <T : Any> getService(clazz: KClass<T>): T {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(clazz.java)
    }
}

