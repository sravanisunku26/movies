package com.lloyds.assignment.custom.module.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lloyds.assignment.custom.module.details.MovieSourceFactory
import com.lloyds.assignment.custom.repo.MovieRepo

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