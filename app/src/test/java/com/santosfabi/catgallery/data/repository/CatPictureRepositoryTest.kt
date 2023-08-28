package com.santosfabi.catgallery.data.repository

import com.santosfabi.catgallery.data.model.GalleryItem
import com.santosfabi.catgallery.data.model.GalleryResponse
import com.santosfabi.catgallery.data.model.PictureDetails
import com.santosfabi.catgallery.data.source.remote.ImgurApiService
import com.santosfabi.catgallery.domain.model.Result
import com.santosfabi.catgallery.util.Constants
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import java.net.SocketTimeoutException

@RunWith(MockitoJUnitRunner::class)
class CatPictureRepositoryTest {

    @Mock
    private lateinit var api: ImgurApiService

    private lateinit var repository: CatPicturesRepositoryImpl

    @Mock
    private lateinit var mockLogger: com.santosfabi.catgallery.util.Logger

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = CatPicturesRepositoryImpl(api, mockLogger)
    }

    @Test
    fun `test getPhotos success`() = runBlocking {
        val response = Response.success(
            GalleryResponse(
                data = listOf(
                    GalleryItem("0", "My yellow Cat", listOf(PictureDetails("", "", "", false))),
                    GalleryItem("0", "My yellow Cat", listOf(PictureDetails("", "", "", false))),
                ),
                success = true,
                status = 200
            )
        )

        Mockito.`when`(api.searchGallery(Constants.SEARCH_TERM)).thenReturn(response)

        val result = repository.getPictures()
        TestCase.assertTrue(result is Result.Success)
    }

    @Test
    fun `test getPhotos success with empty list`() = runBlocking {
        val response = Response.success(
            GalleryResponse(
                data = listOf(),
                success = true,
                status = 200
            )
        )

        Mockito.`when`(api.searchGallery(Constants.SEARCH_TERM)).thenReturn(response)

        val result = repository.getPictures()
        TestCase.assertTrue(result is Result.Success)
        TestCase.assertTrue((result as Result.Success).data.data.isEmpty())
    }

    @Test
    fun `test getPhotos failure due to API exception`() = runBlocking {
        // Mock an exception
        Mockito.`when`(api.searchGallery(Constants.SEARCH_TERM))
            .thenThrow(RuntimeException("Network Error"))

        val result = repository.getPictures()

        TestCase.assertTrue(result is Result.Failure)
        TestCase.assertEquals("Network Error", (result as Result.Failure).error)
    }

    @Test
    fun `test getPhotos failure due to non-successful response`() = runBlocking {
        // Mock a non-successful response
        val errorResponseBody = Response.error<GalleryResponse>(
            400,
            ResponseBody.create("application/json".toMediaTypeOrNull(), "")
        )
        Mockito.`when`(api.searchGallery(Constants.SEARCH_TERM)).thenReturn(errorResponseBody)

        val result = repository.getPictures()

        TestCase.assertTrue(result is Result.Failure)
        TestCase.assertEquals(
            "Failed to fetch photos. Response: ",
            (result as Result.Failure).error
        )
    }

    @Test
    fun `test getPhotos network error`() = runBlocking {
        // Mock a network exception
        Mockito.`when`(api.searchGallery(Constants.SEARCH_TERM))
            .thenAnswer { throw SocketTimeoutException("Timeout") }

        val result = repository.getPictures()

        TestCase.assertTrue(result is Result.Failure)
        TestCase.assertEquals(
            "Network error. Please check your connection.",
            (result as Result.Failure).error
        )
    }


    @Test
    fun `test getPhotos server error`() = runBlocking {
        // Mock a server error response
        val errorResponseBody = Response.error<GalleryResponse>(
            500,
            ResponseBody.create("application/json".toMediaTypeOrNull(), "")
        )
        Mockito.`when`(api.searchGallery(Constants.SEARCH_TERM)).thenReturn(errorResponseBody)

        val result = repository.getPictures()

        TestCase.assertTrue(result is Result.Failure)
        TestCase.assertEquals(
            "Failed to fetch photos. Response: ",
            (result as Result.Failure).error
        )
    }

}
