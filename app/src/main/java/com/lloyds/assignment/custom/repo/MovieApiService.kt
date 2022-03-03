package com.lloyds.assignment.custom.repo

import com.lloyds.assignment.custom.model.MovieDetailResponse
import com.lloyds.assignment.custom.model.PlayingNowResponse
import com.lloyds.assignment.custom.model.PopularListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/now_playing?language=en-US&page=undefined&api_key=55957fcf3ba81b137f8fc01ac5a31fb5")
    fun getPlayingNowResponse(): Call<PlayingNowResponse>

    @GET("movie/popular?api_key=55957fcf3ba81b137f8fc01ac5a31fb5&language=en-US")
    fun getPopularListResponse(@Query("page") pageNumber: Int): Call<PopularListResponse>

    @GET("movie/{MOVIE_ID}?api_key=55957fcf3ba81b137f8fc01ac5a31fb5&language=en-US")
    fun getMovieDetailResponse(@Path("MOVIE_ID") movieId: Int): Call<MovieDetailResponse>
}