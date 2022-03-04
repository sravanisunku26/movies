package com.lloyds.assignment.custom.module.details

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lloyds.assignment.R
import com.lloyds.assignment.custom.model.Genres
import com.lloyds.assignment.custom.model.MovieDetailResponse
import com.lloyds.assignment.custom.module.mainscreen.main.details.MovieDetailViewModelFactory
import com.lloyds.assignment.custom.repo.MovieRepo
import com.lloyds.assignment.custom.utils.EXTRA_MOVIE_ID
import com.lloyds.assignment.databinding.ActivityMovieDetailBinding
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class MovieDetailActivity : AppCompatActivity() {
    private lateinit var activityMovieDetailBinding: ActivityMovieDetailBinding
    private lateinit var viewModel: MovieDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMovieDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        setSupportActionBar(activityMovieDetailBinding.toolbar as Toolbar);
        val movieRepo = MovieRepo(this)
        val factory = MovieDetailViewModelFactory(movieRepo)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        supportActionBar?.setDisplayShowTitleEnabled(false)


        var movieId = 0;
        if (intent.extras!!.containsKey(EXTRA_MOVIE_ID)) {
            movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0)
        }
        viewModel = ViewModelProviders.of(this, factory).get(MovieDetailsViewModel::class.java)
        viewModel.getMovieDetails(movieId).observe(this, Observer {
            Log.v("response:::", it.poster_path)
            activityMovieDetailBinding.result = it
        })
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

@BindingAdapter("app:addGenres")
fun setGenres(view: LinearLayout, movieDetailResponse: MovieDetailResponse?) {
    var genreslist: List<Genres>? = movieDetailResponse?.genres
    if (genreslist != null) {
        for (genre in genreslist) {
            Log.v("response:::", genre.name)
            var textView = TextView(view.context)
            textView.setPadding(10, 10, 10, 10)
            textView.setBackgroundResource(R.drawable.text_background)
            textView.setText(genre.name)
            var layoutParams: android.widget.LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(10, 10, 10, 10);
            textView.layoutParams = layoutParams

            view.addView(textView)
        }
    }

}

@BindingAdapter("android:src")
fun loadRemoteImage(view: ImageView, poster_path: String?) {
    if (!poster_path.isNullOrEmpty()) {
        Picasso.get()
            .load(Uri.parse("https://image.tmdb.org/t/p/original${poster_path}"))
            .placeholder(
                R.drawable.placeholder
            ).into(view);
    } else {
        view.setImageResource(R.drawable.placeholder)
    }
}

@BindingAdapter("setReleaseDate")
fun setReleaseDateAndDuration(view: TextView, movieDetailResponse: MovieDetailResponse?) {
    if (movieDetailResponse != null) {
        val minutes: Long? = movieDetailResponse?.runtime?.toLong()
        val hours = minutes?.let { TimeUnit.MINUTES.toHours(it) }
        val remainMinutes = hours?.let { TimeUnit.HOURS.toMinutes(it) }?.let { minutes?.minus(it) }
        val input_date = movieDetailResponse?.release_date
        if(!input_date.isNullOrEmpty()) {
            val format1 = SimpleDateFormat("yyyy-MM-dd")
            val dt1: Date = format1.parse(input_date)
            val format2 = SimpleDateFormat("MMMM dd, YYYY")



            view.setText(
                format2.format(dt1) + " - " + String.format(
                    "%dh %02dm",
                    hours,
                    remainMinutes
                )
            )
        }
    }

}



