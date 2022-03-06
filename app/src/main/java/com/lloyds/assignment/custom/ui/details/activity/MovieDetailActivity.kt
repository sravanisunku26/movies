package com.lloyds.assignment.custom.ui.details.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.lloyds.assignment.R
import com.lloyds.assignment.custom.repo.MovieRepo
import com.lloyds.assignment.custom.ui.BaseActivity
import com.lloyds.assignment.custom.ui.details.viewmodel.MovieDetailsViewModel
import com.lloyds.assignment.custom.ui.details.viewmodelfactory.MovieDetailViewModelFactory
import com.lloyds.assignment.custom.utils.EXTRA_MOVIE_ID
import com.lloyds.assignment.databinding.ActivityMovieDetailBinding


class MovieDetailActivity : BaseActivity() {
    private lateinit var activityMovieDetailBinding: ActivityMovieDetailBinding
    private lateinit var viewModel: MovieDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMovieDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        setSupportActionBar(activityMovieDetailBinding.toolbar as Toolbar)
        val movieRepo = MovieRepo(this)
        val factory = MovieDetailViewModelFactory(movieRepo)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        var movieId = 0
        if (intent.extras!!.containsKey(EXTRA_MOVIE_ID)) {
            movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0)
        }
        viewModel = ViewModelProvider(this, factory)[MovieDetailsViewModel::class.java]
        viewModel.getMovieDetails(movieId).observe(this) {
            Log.v("response:::", it.poster_path)
            activityMovieDetailBinding.result = it
        }
    }

    override fun networkAvailableCallback() {
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}



