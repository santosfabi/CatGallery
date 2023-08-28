package com.santosfabi.catgallery.data.di

import com.santosfabi.catgallery.plataform.AndroidLogger
import com.santosfabi.catgallery.util.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LoggingModule {
    @Singleton
    @Provides
    fun provideLogger(): Logger {
        return AndroidLogger()
    }
}