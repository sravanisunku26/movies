package com.lloyds.assignment.custom.module.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.lloyds.assignment.custom.model.Results
import com.lloyds.assignment.custom.repo.MovieRepo

class MovieSourceFactory : DataSource.Factory<Int, Results>() {
    val movieRepoLiveData = MutableLiveData<PageKeyedDataSource<Int, Results>>()
    override fun create(): DataSource<Int, Results> {
        val movieRepo = MovieRepo()
        Log.v("MovieSourceFactory","MovieSourceFactory::::")
        movieRepoLiveData.postValue(movieRepo)
        return movieRepo
    }
}