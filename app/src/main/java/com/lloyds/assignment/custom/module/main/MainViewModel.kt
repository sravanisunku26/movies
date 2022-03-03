package com.lloyds.assignment.custom.module.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.lloyds.assignment.custom.module.details.MovieSourceFactory
import com.lloyds.assignment.custom.model.PlayingNowResponse
import com.lloyds.assignment.custom.model.Results
import com.lloyds.assignment.custom.repo.MovieRepo

class MainViewModel : ViewModel() {

    fun getPlayingNowList(context: Context): LiveData<PlayingNowResponse> {

        return MovieRepo().getPlayingNowResponse(context)
    }

    var userPagedList: LiveData<PagedList<Results>>
    private var liveDataSource: MutableLiveData<PageKeyedDataSource<Int,Results>>

    init {
        val itemDataSourceFactory = MovieSourceFactory()
        liveDataSource = itemDataSourceFactory.movieRepoLiveData
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(MovieRepo.PAGE_SIZE)
            .build()
        userPagedList = LivePagedListBuilder(itemDataSourceFactory, config)
            .build()
    }
}