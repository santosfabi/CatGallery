package com.santosfabi.catgallery.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santosfabi.catgallery.data.model.GalleryResponse
import com.santosfabi.catgallery.data.model.PictureDetails
import com.santosfabi.catgallery.data.usecase.FetchPicturesUseCaseImpl
import com.santosfabi.catgallery.domain.model.UiState
import com.santosfabi.catgallery.util.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val useCase: FetchPicturesUseCaseImpl,
    private val logger: Logger
) :
    ViewModel() {

    private val debugTag = "GalleryViewModel"

    private val _galleryState = MutableStateFlow<UiState<List<PictureDetails>>>(UiState.Loading)

    val galleryState: StateFlow<UiState<List<PictureDetails>>> get() = _galleryState

    // Function to fetch pictures and update UI state
    fun fetchPictures() {
        viewModelScope.launch {
            try {
                useCase().collect { state ->
                    when (state) {
                        // Extract and filter pictures (exclude videos and GIFs) from the API response
                        is UiState.Success<GalleryResponse> -> {
                            val allPictures = state.data.data.flatMap { it.images ?: listOf() }
                                .filter { it.animated != true }
                            _galleryState.value = UiState.Success(allPictures)
                        }

                        is UiState.Failure -> {
                            _galleryState.value = UiState.Failure("Error fetching pictures")
                            logger.e(debugTag, "Error fetching pictures: ${state.error}")
                        }

                        is UiState.Loading -> {
                            _galleryState.value = UiState.Loading
                        }

                    }
                }
            } catch (e: Exception) {
                _galleryState.value = UiState.Failure(e.localizedMessage ?: "An error occurred")
                logger.e(debugTag, "Error fetching pictures: ${e.localizedMessage}")
            }
        }
    }

    init {
        fetchPictures()
    }
}
