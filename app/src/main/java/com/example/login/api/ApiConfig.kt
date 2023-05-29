package com.example.login.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object ApiConfig {
    var authToken: String? = null

    private val okhttp = OkHttpClient.Builder()
//        .addInterceptor { chain ->
//            chain.proceed(chain.request().newBuilder().also {
//                it.addHeader("Authorization", "Bearer $authToken")
//            }.build())
//        }
        .apply {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(loggingInterceptor)
        }
        .readTimeout(25, TimeUnit.SECONDS)
        .writeTimeout(300, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://story-api.dicoding.dev/v1/")
        .client(okhttp)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val dicodingStoryService = retrofit.create<ApiService>()
}