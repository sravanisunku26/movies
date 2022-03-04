package com.lloyds.assignment.custom.module.details

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.lloyds.assignment.custom.model.Results
import com.lloyds.assignment.custom.repo.MovieRepo

class MovieSourceFactory (val context: Context): DataSource.Factory<Int, Results>() {
    val movieRepoLiveData = MutableLiveData<PageKeyedDataSource<Int, Results>>()

    override fun create(): DataSource<Int, Results> {
        val movieRepo = MovieRepo(context)
        Log.v("MovieSourceFactory","MovieSourceFactory::::")
        movieRepoLiveData.postValue(movieRepo)
        return movieRepo
    }
}