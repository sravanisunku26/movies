package com.lloyds.assignment.custom.ui.details.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lloyds.assignment.custom.ui.details.viewmodel.MovieDetailsViewModel
import com.lloyds.assignment.custom.repo.MovieRepo

class MovieDetailViewModelFactory(private val movieRepo: MovieRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)){
            return MovieDetailsViewModel(movieRepo) as T
        }
        throw IllegalArgumentException ("UnknownViewModel")
    }

}