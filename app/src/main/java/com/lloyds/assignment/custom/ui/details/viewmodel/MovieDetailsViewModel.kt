package com.lloyds.assignment.custom.ui.details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lloyds.assignment.custom.model.MovieDetailResponse
import com.lloyds.assignment.custom.repo.MovieRepo

class MovieDetailsViewModel(private val movieRepo: MovieRepo): ViewModel() {

    fun getMovieDetails(movieId:Int) : LiveData<MovieDetailResponse>{
       return movieRepo.getMovieDetailInfo(movieId)
    }
}