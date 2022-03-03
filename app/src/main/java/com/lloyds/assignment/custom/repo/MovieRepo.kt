package com.lloyds.assignment.custom.repo

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.lloyds.assignment.custom.model.MovieDetailResponse
import com.lloyds.assignment.custom.model.PlayingNowResponse
import com.lloyds.assignment.custom.model.PopularListResponse
import com.lloyds.assignment.custom.model.Results
import com.lloyds.assignment.custom.utils.Utils
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieRepo : PageKeyedDataSource<Int, Results>() {
    private val FIRST_PAGE = 1
    private lateinit var context: Context

    companion object {
        private val myOkHttpClient = OkHttpClient().newBuilder()
            .build()

        val baseUrl = "https://api.themoviedb.org/3/"
        val retrofit = Retrofit.Builder()
            .client(myOkHttpClient)
            .baseUrl(baseUrl)

            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(MovieApiService::class.java)
        var PAGE_SIZE = 6
    }

    fun getPlayingNowResponse(context: Context): LiveData<PlayingNowResponse> {
        Log.v("getPlayingNowResponse", "getPlayingNowResponse")
        val liveData = MutableLiveData<PlayingNowResponse>()
        this.context = context
        if (Utils.internetCheck(context)) {
            service.getPlayingNowResponse().enqueue(object : Callback<PlayingNowResponse> {
                override fun onResponse(
                    call: Call<PlayingNowResponse>,
                    response: Response<PlayingNowResponse>
                ) {
                    Log.v("getPlayingNowResponse", response.code().toString())
                    if (response.code() == 200) {
                        val playingNowResponse = response.body()
                        liveData.value = response.body()

                        Log.v("onsuccess", playingNowResponse.toString())
                    } else {
                        Log.v("onsuccess", response.message())
                    }
                }

                override fun onFailure(call: Call<PlayingNowResponse>, t: Throwable) {
                    Log.v("onFailure", t.toString())

                }

            })
        }
        return liveData
    }

    fun getMovieDetailInfo(movieId: Int, context: Context): LiveData<MovieDetailResponse> {
        val liveData = MutableLiveData<MovieDetailResponse>()
        this.context = context
        if (Utils.internetCheck(context)) {
            service.getMovieDetailResponse(movieId).enqueue(object : Callback<MovieDetailResponse> {
                override fun onResponse(
                    call: Call<MovieDetailResponse>,
                    response: Response<MovieDetailResponse>
                ) {
                    Log.v("getMovieDetailResponse", response.code().toString())
                    if (response.code() == 200) {
                        liveData.value = response.body()
                    } else
                        Log.v("onsuccess", response.message())
                }

                override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                    Log.v("onFailure", t.toString())

                }
            })
        }
        return liveData
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Results>
    ) {
        var response: Response<PopularListResponse> =
            service.getPopularListResponse(FIRST_PAGE).execute();

        val apiResponse = response.body()!!
        val results = apiResponse.results;
        PAGE_SIZE = apiResponse.total_pages;
        Log.v("loadInitial", results.toString())
        apiResponse?.let {
            callback.onResult(results, null, FIRST_PAGE + 1)
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Results>
    ) {
        service.getPopularListResponse(params.key)
            .enqueue(object : Callback<PopularListResponse> {
                override fun onResponse(
                    call: Call<PopularListResponse>,
                    response: Response<PopularListResponse>
                ) {
                    if (response.isSuccessful) {
                        val apiResponse = response.body()!!
                        val results: List<Results> = apiResponse.results
                        var listResponse = ArrayList<PopularListResponse>();
                        listResponse.add(apiResponse)
                        Log.v("loadBefore", results.toString())
                        val key = if (params.key > 1) params.key - 1 else 0
                        apiResponse?.let {
                            callback.onResult(results, key)
                        }
                    }

                }

                override fun onFailure(call: Call<PopularListResponse>, t: Throwable) {
                }

            })
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Results>
    ) {
        service.getPopularListResponse(params.key)
            .enqueue(object : Callback<PopularListResponse> {
                override fun onResponse(
                    call: Call<PopularListResponse>,
                    response: Response<PopularListResponse>
                ) {
                    if (response.isSuccessful) {
                        val apiResponse = response.body()!!
                        val results = apiResponse.results
                        val key = params.key + 1
                        Log.v("loadAfter", results.toString())

                        apiResponse?.let {
                            callback.onResult(results, key)
                        }
                    }

                }

                override fun onFailure(call: Call<PopularListResponse>, t: Throwable) {
                }

            })
    }
}