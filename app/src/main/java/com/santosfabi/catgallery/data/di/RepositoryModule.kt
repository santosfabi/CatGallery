package com.santosfabi.catgallery.data.di

import com.santosfabi.catgallery.data.repository.CatPicturesRepositoryImpl
import com.santosfabi.catgallery.data.source.remote.ImgurApiService
import com.santosfabi.catgallery.domain.repository.CatPicturesRepository
import com.santosfabi.catgallery.util.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideCatPicturesRepository(
        api: ImgurApiService,
        logger: Logger
    ): CatPicturesRepository {
        return CatPicturesRepositoryImpl(api, logger)
    }
}