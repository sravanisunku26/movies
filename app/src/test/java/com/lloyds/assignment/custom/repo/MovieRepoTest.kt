package com.lloyds.assignment.custom.repo

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.Assert.*
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.powermock.core.classloader.annotations.PrepareForTest
import java.net.HttpURLConnection

@RunWith(MockitoJUnitRunner::class)
@PrepareForTest(Log::class)
class MovieRepoTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var movieRepo: MovieRepo

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: MovieApiService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        movieRepo = MovieRepo()
        mockWebServer = MockWebServer()
        mockWebServer.start()
        apiService = MovieRepo.service
    }

    @Test
    fun `read sample success json file`() {
        val reader = MockResponseFileReader("playingnowresponse.json")
        assertNotNull(reader.content)
    }
    @Test
    fun `fetch details and check response Code 200 returned`(){
        // Act
        val actualResponse = apiService.getPlayingNowResponse().execute()
        Log.v("test::::", actualResponse.code().toString())
        // Assert
        assertTrue(actualResponse.code().toString().contains("200"))
    }

    @Test
    fun `fetch details for playingnow response not null`(){
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("playingnowresponse.json").content)
        mockWebServer.enqueue(response)
        val mockResponse = response.getBody()?.readUtf8()
        // Act
        val actualResponse = apiService.getPlayingNowResponse().execute()
        Log.v("test::::", actualResponse.code().toString())
        // Assert
        assertFalse(actualResponse.body().toString().isEmpty())
    }



    @Test
    fun `fetch playing now and check response success returned`() {
        // Assign
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("playingnowresponse.json").content)
        mockWebServer.enqueue(response)
        val mockResponse = response.getBody()?.readUtf8()
        // Act
        val actualResponse = apiService.getPlayingNowResponse().execute()
        // Assert
        assertEquals(
            mockResponse?.let { `parse mocked JSON response`(it) },
            actualResponse.body()?.total_pages
        )
    }

    private fun `parse mocked JSON response`(mockResponse: String): Int {
        val reader = JSONObject(mockResponse)
        return reader.getInt("total_pages")
    }

    @Test
    fun `fetch movie details and check response success returned`() {
        // Assign
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("movieDetailResponse.json").content)
        mockWebServer.enqueue(response)
        val mockResponse = response.getBody()?.readUtf8()
        val reader = JSONObject(mockResponse)
        val voteCount = reader.getInt("vote_count")

        // Act
        val actualResponse = apiService.getMovieDetailResponse(768744).execute()
        // Assert
        assertEquals(voteCount, actualResponse.body()?.vote_count)
    }

    @Test
    fun testLoadInitial() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("loadIntialResponse.json").content)
        mockWebServer.enqueue(response)
        val mockResponse = response.getBody()?.readUtf8()
        val reader = JSONObject(mockResponse)
        val voteCount = reader.getInt("total_pages")

        // Act
        val actualResponse = apiService.getPopularListResponse(1).execute()
        // Assert
        assertEquals(voteCount, actualResponse.body()?.total_pages)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

}