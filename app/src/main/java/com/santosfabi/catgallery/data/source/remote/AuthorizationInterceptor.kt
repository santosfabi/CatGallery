package com.santosfabi.catgallery.data.source.remote

import com.santosfabi.catgallery.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        val requestBuilder = originRequest.newBuilder()

        val apiKey = BuildConfig.IMGUR_CLIENT_ID
        requestBuilder.addHeader("Authorization", "Client-ID $apiKey")

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}