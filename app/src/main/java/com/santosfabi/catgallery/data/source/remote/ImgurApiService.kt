package com.santosfabi.catgallery.data.source.remote

import com.santosfabi.catgallery.data.model.GalleryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImgurApiService {
    @GET("3/gallery/search/")
    suspend fun searchGallery(@Query("q") query: String): Response<GalleryResponse>
}
