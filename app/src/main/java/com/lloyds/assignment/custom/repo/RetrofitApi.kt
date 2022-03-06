package com.lloyds.assignment.custom.repo

import com.lloyds.assignment.custom.utils.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitApi {
    private fun createRetrofit(): Retrofit {
        val myOkHttpClient = OkHttpClient().newBuilder()
            .build()
        return Retrofit.Builder()
           .client(myOkHttpClient)
           .baseUrl(BASE_URL)
           .addConverterFactory(GsonConverterFactory.create())
           .build()
    }

    val retrofitService: MovieApiService by lazy {
        createRetrofit().create(MovieApiService::class.java)
    }
}