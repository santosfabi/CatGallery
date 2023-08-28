package com.santosfabi.catgallery.data.usecase

import com.santosfabi.catgallery.data.model.GalleryItem
import com.santosfabi.catgallery.data.model.GalleryResponse
import com.santosfabi.catgallery.data.model.PictureDetails
import com.santosfabi.catgallery.domain.model.Result
import com.santosfabi.catgallery.domain.model.UiState
import com.santosfabi.catgallery.domain.repository.CatPicturesRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class FetchPicturesUseCaseImplTest {

    @Mock
    private lateinit var repository: CatPicturesRepository

    private lateinit var useCase: FetchPicturesUseCaseImpl

    @Mock
    private lateinit var mockLogger: com.santosfabi.catgallery.util.Logger

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        MockitoAnnotations.openMocks(this)
        useCase = FetchPicturesUseCaseImpl(repository, mockLogger)
    }

    @Test
    fun `invoke emits loading and then success state when repository returns success`() =
        runBlocking {
            val mockResponse = GalleryResponse(
                data = listOf(
                    GalleryItem("0", "My yellow Cat", ArrayList()),
                    GalleryItem("1", "My Blue Cat", ArrayList())
                ),
                success = true,
                status = 200
            )

            Mockito.`when`(repository.getPictures()).thenReturn(Result.Success(mockResponse))

            val result = useCase.invoke().toList()

            Assert.assertEquals(2, result.size)
            Assert.assertEquals(UiState.Loading, result[0])
            Assert.assertEquals(UiState.Success(mockResponse), result[1])
        }

    @Test
    fun `invoke emits loading and then success state when repository returns empty list`() =
        runBlocking {
            val mockResponse = GalleryResponse(
                data = listOf(),
                success = true,
                status = 200
            )

            Mockito.`when`(repository.getPictures()).thenReturn(Result.Success(mockResponse))

            val result = useCase.invoke().toList()

            Assert.assertEquals(2, result.size)
            Assert.assertEquals(UiState.Loading, result[0])
            Assert.assertEquals(UiState.Success(mockResponse), result[1])
        }

    @Test
    fun `invoke emits loading and then failure state when repository returns failure`() =
        runBlocking {
            val errorMessage = "An error occurred"
            Mockito.`when`(repository.getPictures()).thenReturn(Result.Failure(errorMessage))

            val result = useCase.invoke().toList()

            Assert.assertEquals(2, result.size)
            Assert.assertEquals(UiState.Loading, result[0])
            Assert.assertEquals(UiState.Failure(errorMessage), result[1])
        }

    @Test
    fun `invoke emits loading and then success state when repository returns animated images`() =
        runBlocking {
            val mockResponse = GalleryResponse(
                data = listOf(
                    GalleryItem("0", "Animated Cat", listOf(PictureDetails("0", "link", "", true)))
                ),
                success = true,
                status = 200
            )

            Mockito.`when`(repository.getPictures()).thenReturn(Result.Success(mockResponse))

            val result = useCase.invoke().toList()

            Assert.assertEquals(2, result.size)
            Assert.assertEquals(UiState.Loading, result[0])
            Assert.assertEquals(UiState.Success(mockResponse), result[1])
        }

}
