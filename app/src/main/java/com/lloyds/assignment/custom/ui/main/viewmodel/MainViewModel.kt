package com.lloyds.assignment.custom.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.lloyds.assignment.custom.model.PlayingNowResponse
import com.lloyds.assignment.custom.model.Results
import com.lloyds.assignment.custom.ui.details.viewmodelfactory.MovieSourceFactory
import com.lloyds.assignment.custom.repo.MovieRepo
import com.lloyds.assignment.custom.utils.PAGE_SIZE

class MainViewModel(movieRepo: MovieRepo, movieSourceFactory: MovieSourceFactory) : ViewModel() {
     var callPlayingNowApi: MutableLiveData<String>? = null
     var callMostPopularApi: MutableLiveData<String>? = null
    private var playingNowResponse: LiveData<PlayingNowResponse>

    fun getPlayingNowList(): LiveData<PlayingNowResponse> {
        return playingNowResponse
    }

    var userPagedList: LiveData<PagedList<Results>>
    private var liveDataSource: MutableLiveData<PageKeyedDataSource<Int,Results>>

    init {
        liveDataSource = movieSourceFactory.movieRepoLiveData
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGE_SIZE)
            .build()
        callMostPopularApi = MutableLiveData()
        userPagedList = Transformations.switchMap(callMostPopularApi!!){LivePagedListBuilder(movieSourceFactory, config)
            .build()}
        callPlayingNowApi = MutableLiveData()
        playingNowResponse = Transformations.switchMap(callPlayingNowApi!!){movieRepo.getPlayingNowResponse()}
    }

}