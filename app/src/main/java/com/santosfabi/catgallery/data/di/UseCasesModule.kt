package com.santosfabi.catgallery.data.di

import com.santosfabi.catgallery.data.usecase.FetchPicturesUseCaseImpl
import com.santosfabi.catgallery.domain.repository.CatPicturesRepository
import com.santosfabi.catgallery.domain.usecase.FetchPicturesUseCase
import com.santosfabi.catgallery.util.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Singleton
    @Provides
    fun provideFetchPictureImpl(
        repository: CatPicturesRepository,
        logger: Logger
    ): FetchPicturesUseCase {
        return FetchPicturesUseCaseImpl(repository, logger)
    }
}