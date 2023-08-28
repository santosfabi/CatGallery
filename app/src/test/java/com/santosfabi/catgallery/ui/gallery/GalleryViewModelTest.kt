package com.santosfabi.catgallery.ui.gallery

import com.santosfabi.catgallery.data.model.GalleryItem
import com.santosfabi.catgallery.data.model.GalleryResponse
import com.santosfabi.catgallery.data.model.PictureDetails
import com.santosfabi.catgallery.domain.model.UiState
import com.santosfabi.catgallery.data.usecase.FetchPicturesUseCaseImpl
import com.santosfabi.catgallery.util.Logger
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class GalleryViewModelTest {

    private lateinit var mockLogger: Logger
    private lateinit var useCase: FetchPicturesUseCaseImpl
    private val testDispatcher = TestCoroutineDispatcher()
    private val viewModel by lazy { GalleryViewModel(useCase, mockLogger) }

    @Before
    fun setUp() {
        mockLogger = Mockito.mock(Logger::class.java)
        useCase = Mockito.mock(FetchPicturesUseCaseImpl::class.java)
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @Test
    fun `when fetchPictures is called and useCase returns success, galleryState should be Success`() =
        testDispatcher.runBlockingTest {
            // Given
            val galleryResponse = GalleryResponse(
                data = listOf(
                    GalleryItem(
                        "0",
                        "My yellow Cat",
                        listOf(PictureDetails("0", "link", "", false))
                    ),
                    GalleryItem(
                        "1",
                        "My Blue Cat",
                        listOf(PictureDetails("1", "link", "", false))
                    )
                ),
                success = true,
                status = 200
            )

            val expectedData =
                galleryResponse.data.flatMap { it.images }.filter { it.animated != true }

            whenever(useCase()).thenReturn(flowOf(UiState.Success(galleryResponse)))

            // When
            viewModel.fetchPictures()

            // Then
            TestCase.assertTrue(viewModel.galleryState.value is UiState.Success)
            TestCase.assertEquals(
                expectedData,
                (viewModel.galleryState.value as UiState.Success).data
            )
        }

    @Test
    fun `when fetchPictures is called and useCase returns empty list, galleryState should be Success with empty list`() =
        testDispatcher.runBlockingTest {
            // Given
            val galleryResponse = GalleryResponse(
                data = listOf(),
                success = true,
                status = 200
            )

            listOf<PictureDetails>()

            whenever(useCase()).thenReturn(flowOf(UiState.Success(galleryResponse)))

            // When
            viewModel.fetchPictures()

            // Then
            TestCase.assertTrue(viewModel.galleryState.value is UiState.Success)
            TestCase.assertTrue((viewModel.galleryState.value as UiState.Success).data.isEmpty())
        }

    @Test
    fun `when fetchPictures is called and useCase returns failure, galleryState should be Failure`() =
        testDispatcher.runBlockingTest {
            // Given
            val errorMsg = "Error fetching pictures"
            whenever(useCase()).thenReturn(flowOf(UiState.Failure(errorMsg)))

            // When
            viewModel.fetchPictures()

            // Then
            TestCase.assertTrue(viewModel.galleryState.value is UiState.Failure)
            TestCase.assertEquals(errorMsg, (viewModel.galleryState.value as UiState.Failure).error)
        }

    @After
    fun tearDown() {
        // Clean up coroutines
        testDispatcher.cleanupTestCoroutines()
        Dispatchers.resetMain()
    }
}
