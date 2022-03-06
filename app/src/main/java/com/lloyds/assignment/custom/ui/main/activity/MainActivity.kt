package com.lloyds.assignment.custom.ui.main.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lloyds.assignment.R
import com.lloyds.assignment.custom.model.Results
import com.lloyds.assignment.custom.repo.MovieRepo
import com.lloyds.assignment.custom.ui.BaseActivity
import com.lloyds.assignment.custom.ui.details.activity.MovieDetailActivity
import com.lloyds.assignment.custom.ui.details.viewmodelfactory.MovieSourceFactory
import com.lloyds.assignment.custom.ui.main.adapter.PlayingNowMoviesAdapter
import com.lloyds.assignment.custom.ui.main.adapter.PopularListMoviesAdapter
import com.lloyds.assignment.custom.ui.main.viewmodel.MainViewModel
import com.lloyds.assignment.custom.ui.main.viewmodelfactory.MainViewModelFactory
import com.lloyds.assignment.custom.utils.EXTRA_MOVIE_ID
import com.lloyds.assignment.databinding.ActivityMainBinding

/**
 * Main screen having both Playingnow and Popularvlist of movies
 */
class MainActivity : BaseActivity(), PlayingNowMoviesAdapter.OnItemClickListener,
    PopularListMoviesAdapter.OnItemClickListener {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private var moviesAdapter: PlayingNowMoviesAdapter? = null
    private var popularListMoviesAdapter: PopularListMoviesAdapter? = null
    private lateinit var movieRepo: MovieRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        movieRepo = MovieRepo(this)
        val itemDataSourceFactory = MovieSourceFactory(this)
        val viewModelFactory =
            MainViewModelFactory(movieRepo, itemDataSourceFactory)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        activityMainBinding.playingNowRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        activityMainBinding.mostPopularRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        viewModel.callPlayingNowApi?.value = getString(R.string.playing_now)
        //Playing now Movies list adding to Recyclerview
        viewModel.getPlayingNowList().observe(this) {
            if (moviesAdapter == null) {
                moviesAdapter = PlayingNowMoviesAdapter(it, this)
                activityMainBinding.playingNowRv.adapter = moviesAdapter
            }
        }
        viewModel.callMostPopularApi?.value = getString(R.string.most_popular)
        //Popular Movies List adding to recyclerview
        viewModel.userPagedList.observe(this) {
            popularListMoviesAdapter = PopularListMoviesAdapter(this)
            popularListMoviesAdapter!!.submitList(it)
            activityMainBinding.mostPopularRv.adapter = popularListMoviesAdapter
        }
    }

    /**
     * Updating UI after data connection available
     *
     */
    override fun networkAvailableCallback() {
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.callPlayingNowApi?.value = getString(R.string.playing_now)
            viewModel.callMostPopularApi?.value = getString(R.string.most_popular)
            activityMainBinding.invalidateAll()
        }, 1000)
    }


    /**
     * Navigating to movie detail screen when click on movie item
     */
    override fun onItemClick(item: Results?) {
            val intent = Intent(this, MovieDetailActivity::class.java)
            Log.v("movie_id", (item?.id).toString())
            intent.putExtra(EXTRA_MOVIE_ID, item?.id)
            startActivity(intent)
    }
}

