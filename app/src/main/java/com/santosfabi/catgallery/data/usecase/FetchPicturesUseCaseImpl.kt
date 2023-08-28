package com.santosfabi.catgallery.data.usecase

import com.santosfabi.catgallery.data.model.GalleryResponse
import com.santosfabi.catgallery.domain.model.Result
import com.santosfabi.catgallery.domain.model.UiState
import com.santosfabi.catgallery.domain.repository.CatPicturesRepository
import com.santosfabi.catgallery.domain.usecase.FetchPicturesUseCase
import com.santosfabi.catgallery.util.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

open class FetchPicturesUseCaseImpl @Inject constructor(
    private val repository: CatPicturesRepository,
    private val logger: Logger
): FetchPicturesUseCase {
    private val debugTag = "FetchPicsUseCase"

    /**
     * Fetches pictures from the repository and returns the result wrapped in a UI state.
     */
    override suspend operator fun invoke(): Flow<UiState<GalleryResponse>> = flow {
        emit(UiState.Loading)

        when (val result = repository.getPictures()) {
            is Result.Success -> {
                emit(UiState.Success(result.data))

            }

            is Result.Failure -> {
                logger.e(debugTag, "Error fetching photos: ${result.error}")
                emit(UiState.Failure(result.error))
            }
        }
    }
}