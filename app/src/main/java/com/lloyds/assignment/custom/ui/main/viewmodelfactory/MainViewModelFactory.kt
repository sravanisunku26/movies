package com.lloyds.assignment.custom.ui.main.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lloyds.assignment.custom.ui.details.viewmodelfactory.MovieSourceFactory
import com.lloyds.assignment.custom.repo.MovieRepo
import com.lloyds.assignment.custom.ui.main.viewmodel.MainViewModel

class MainViewModelFactory(private val movieRepo: MovieRepo, private val movieSourceFactory: MovieSourceFactory) :
    ViewModelProvider.NewInstanceFactory() {
    override
    fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(movieRepo, movieSourceFactory) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}