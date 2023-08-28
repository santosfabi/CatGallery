package com.santosfabi.catgallery.data.model

data class GalleryResponse(
    val data: List<GalleryItem>,
    val success: Boolean,
    val status: Int
)
