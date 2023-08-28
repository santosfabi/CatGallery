package com.santosfabi.catgallery.data.repository

import com.santosfabi.catgallery.data.model.GalleryResponse
import com.santosfabi.catgallery.data.source.remote.ImgurApiService
import com.santosfabi.catgallery.domain.model.Result
import com.santosfabi.catgallery.domain.repository.CatPicturesRepository
import com.santosfabi.catgallery.util.Constants
import com.santosfabi.catgallery.util.Logger
import java.io.IOException
import javax.inject.Inject

class CatPicturesRepositoryImpl @Inject constructor(
    private val api: ImgurApiService,
    private val logger: Logger
) : CatPicturesRepository {
    private val debugTag = "CatPicturesRepositoryImpl"

    // Fetch pictures from the Imgur API
    override suspend fun getPictures(): Result<GalleryResponse> {
        return try {
            val response = api.searchGallery(Constants.SEARCH_TERM)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Failure("Response body is null")
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                logger.d(debugTag, "Error fetching pictures: $errorMessage")

                Result.Failure("Failed to fetch photos. Response: $errorMessage")
            }
        } catch (e: IOException) {
            logger.d(debugTag, "IOException: ${e.localizedMessage}")
            Result.Failure("Network error. Please check your connection.")
        } catch (e: Exception) {
            logger.d(debugTag, "Exception ${e.localizedMessage}")
            Result.Failure(e.localizedMessage ?: "An unexpected error occurred")
        }
    }
}
