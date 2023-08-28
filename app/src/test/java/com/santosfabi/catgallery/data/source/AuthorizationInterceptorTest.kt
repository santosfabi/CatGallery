package com.santosfabi.catgallery.data.source

import com.santosfabi.catgallery.data.source.remote.AuthorizationInterceptor
import junit.framework.TestCase
import okhttp3.Interceptor
import okhttp3.Request
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor

class AuthorizationInterceptorTest {

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var chain: Interceptor.Chain

    @Mock
    lateinit var request: Request

    @Mock
    lateinit var response: okhttp3.Response

    private lateinit var interceptor: AuthorizationInterceptor

    @Before
    fun setUp() {
        interceptor = AuthorizationInterceptor()
        `when`(chain.request()).thenReturn(request)
        `when`(chain.proceed(any())).thenReturn(response)
    }

    @Test
    fun `interceptor should add authorization header`() {
        // Given a basic request
        val originalRequest = Request.Builder().url("https://example.com").build()
        `when`(chain.request()).thenReturn(originalRequest)

        // When the interceptor is applied
        interceptor.intercept(chain)

        // Then verify the modified request contains the expected headers
        val captor = argumentCaptor<Request>()
        verify(chain).proceed(captor.capture())

        val modifiedRequest = captor.firstValue
        TestCase.assertEquals("Client-ID 0c0a46db7b96803", modifiedRequest.header("Authorization"))
    }


    // Add more tests for other headers or conditions
}
