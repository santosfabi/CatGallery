package com.santosfabi.catgallery.domain.repository

import com.santosfabi.catgallery.data.model.GalleryResponse
import com.santosfabi.catgallery.domain.model.Result

interface CatPicturesRepository {
    suspend fun getPictures(): Result<GalleryResponse>
}