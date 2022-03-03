package com.lloyds.assignment.custom.module.details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lloyds.assignment.custom.model.MovieDetailResponse
import com.lloyds.assignment.custom.repo.MovieRepo

class MovieDetailsViewModel(private val movieRepo: MovieRepo): ViewModel() {

    fun getMovieDetails(movieId:Int, context: Context) : LiveData<MovieDetailResponse>{
       return movieRepo.getMovieDetailInfo(movieId, context)
    }
}