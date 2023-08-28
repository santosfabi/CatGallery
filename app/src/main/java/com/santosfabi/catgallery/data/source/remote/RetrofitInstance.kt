package com.santosfabi.catgallery.data.source.remote

import com.santosfabi.catgallery.util.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(AuthorizationInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.API_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: ImgurApiService = retrofit.create(ImgurApiService::class.java)
}
