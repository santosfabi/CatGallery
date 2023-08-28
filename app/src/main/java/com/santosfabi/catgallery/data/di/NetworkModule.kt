package com.santosfabi.catgallery.data.di

import com.santosfabi.catgallery.data.source.remote.AuthorizationInterceptor
import com.santosfabi.catgallery.data.source.remote.ImgurApiService
import com.santosfabi.catgallery.data.source.remote.RetrofitInstance

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthorizationInterceptor())
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(): RetrofitInstance {
        return RetrofitInstance
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: RetrofitInstance): ImgurApiService = retrofit.api
}
