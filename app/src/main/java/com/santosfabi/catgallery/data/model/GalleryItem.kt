package com.santosfabi.catgallery.data.model

data class GalleryItem(
    val id: String,
    val title: String,
    val images: List<PictureDetails>,
)