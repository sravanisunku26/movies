package com.lloyds.assignment.custom.module.main

import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lloyds.assignment.R
import com.lloyds.assignment.custom.model.Results
import com.lloyds.assignment.custom.module.details.MovieDetailActivity
import com.lloyds.assignment.custom.movie.PlayingNowMoviesAdapter
import com.lloyds.assignment.custom.movie.PopularListMoviesAdapter
import com.lloyds.assignment.custom.utils.EXTRA_MOVIE_ID
import com.lloyds.assignment.custom.utils.Utils
import com.lloyds.assignment.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), PlayingNowMoviesAdapter.OnItemClickListener,
    PopularListMoviesAdapter.OnItemClickListener {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var moviesAdapter: PlayingNowMoviesAdapter
    private lateinit var popularListMoviesAdapter: PopularListMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.getSupportActionBar()?.hide();
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(MainViewModel::class.java)
        //Data connection checking
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        val connectivityManager =
            getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)

        if(Utils.internetCheck(this)) {
            activityMainBinding.playingNowRv.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

            popularListMoviesAdapter = PopularListMoviesAdapter(this)
            activityMainBinding.mostPopularRv.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

            //Playing now Movies list adding to Recyclerview
            viewModel.getPlayingNowList(this).observe(this, Observer {
                moviesAdapter = PlayingNowMoviesAdapter(it, this)
                activityMainBinding.playingNowRv.adapter = moviesAdapter
            })

            //Popular Movies List adding to recyclerview
            viewModel.userPagedList.observe(this, Observer {
                popularListMoviesAdapter.submitList(it)
                activityMainBinding.mostPopularRv.adapter = popularListMoviesAdapter
            })
        }else{
            Toast.makeText(this,getString(R.string.please_connect_internet),Toast.LENGTH_SHORT).show()
        }
    }


    /**
     * Navigating to movie detail screen when click on movie item
     */
    override fun onItemClick(item: Results?) {
        var intent = Intent(this, MovieDetailActivity::class.java)
        Log.v("movie_id", (item?.id).toString())
        intent.putExtra(EXTRA_MOVIE_ID, item?.id);
        startActivity(intent)
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        // network is available for use
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
        }

        // Network capabilities have changed for the network
        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
        }

        // lost network connection
        override fun onLost(network: Network) {
            super.onLost(network)
            Toast.makeText(
                this@MainActivity,
                getString(R.string.network_connection_lost),
                Toast.LENGTH_SHORT
            ).show()
        }
    }


}

