package com.lloyds.assignment.custom.databindingadapter

import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.lloyds.assignment.R
import com.lloyds.assignment.custom.model.Genres
import com.lloyds.assignment.custom.model.MovieDetailResponse
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@BindingAdapter("app:addGenres")
fun setGenres(view: LinearLayout, movieDetailResponse: MovieDetailResponse?) {
    val genreslist: List<Genres>? = movieDetailResponse?.genres
    if (genreslist != null) {
        for (genre in genreslist) {
            Log.v("response:::", genre.name)
            val textView = TextView(view.context)
            textView.setPadding(10, 10, 10, 10)
            textView.setBackgroundResource(R.drawable.text_background)
            textView.text = genre.name
            val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(10, 10, 10, 10)
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
            ).into(view)
    } else {
        view.setImageResource(R.drawable.placeholder)
    }
}

@BindingAdapter("setReleaseDate")
fun setReleaseDateAndDuration(view: TextView, movieDetailResponse: MovieDetailResponse?) {
    if (movieDetailResponse != null) {
        val minutes: Long = movieDetailResponse.runtime.toLong()
        val hours = minutes.let { TimeUnit.MINUTES.toHours(it) }
        val remainMinutes = hours.let { TimeUnit.HOURS.toMinutes(it) }.let { minutes.minus(it) }
        val inputDate = movieDetailResponse.release_date
        if (inputDate.isNotEmpty()) {
            val format1 = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH )
            val dt1: Date = format1.parse(inputDate)!!
            val format2 = SimpleDateFormat("MMMM dd, YYYY", Locale.ENGLISH)
            view.text = String.format(format2.format(dt1) , String.format(
                "%dh %02dm",
                hours,
                remainMinutes
            ))
        }
    }
}
