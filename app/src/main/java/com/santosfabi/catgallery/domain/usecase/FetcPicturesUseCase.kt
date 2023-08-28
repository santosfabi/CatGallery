package com.santosfabi.catgallery.domain.usecase

import com.santosfabi.catgallery.data.model.GalleryResponse
import com.santosfabi.catgallery.domain.model.UiState
import kotlinx.coroutines.flow.Flow

interface FetchPicturesUseCase {
    suspend operator fun invoke(): Flow<UiState<GalleryResponse>>
}