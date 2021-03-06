package com.lloyds.assignment.custom.ui.main.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lloyds.assignment.R
import com.lloyds.assignment.custom.model.Results
import com.lloyds.assignment.databinding.MostPopularChildLayoutBinding
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

/**
 * Adapter for displaying Popular Movie List
 */
class PopularListMoviesAdapter(private var onItemClickListener: PlayingNowMoviesAdapter.OnItemClickListener) :
    PagedListAdapter<Results, PopularListMoviesAdapter.UserViewHolder>(USER_COMPARATOR) {

    private lateinit var mostPopularChildLayoutBinding: MostPopularChildLayoutBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        mostPopularChildLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.most_popular_child_layout,
            parent,
            false
        )
        return UserViewHolder(mostPopularChildLayoutBinding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val result = getItem(position)
        result?.let { holder.bind(it, onItemClickListener) }
    }

    class UserViewHolder(private var popularChildLayoutBinding: MostPopularChildLayoutBinding) :
        RecyclerView.ViewHolder(popularChildLayoutBinding.root) {
        fun bind(
            results: Results,
            onItemClickListener: PlayingNowMoviesAdapter.OnItemClickListener
        ) {
            popularChildLayoutBinding.result = results
            popularChildLayoutBinding.executePendingBindings()
            popularChildLayoutBinding.populatRl.setOnClickListener {
                onItemClickListener.onItemClick(
                    results
                )
            }
            Picasso.get()
                .load(Uri.parse("https://image.tmdb.org/t/p/original${results.poster_path}"))
                .placeholder(R.drawable.placeholder)
                .into(popularChildLayoutBinding.thumbnailIv)
            val inputDate = results.release_date
            if(!inputDate.isNullOrEmpty()) {
                val format1 = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                val dt1: Date = format1.parse(inputDate)!!
                val format2 = SimpleDateFormat("MMMM dd, YYYY", Locale.ENGLISH)
                popularChildLayoutBinding.releaseDate.text = format2.format(dt1)
            }

            val voteAvg: Float? = results.vote_average?.times(10)
            popularChildLayoutBinding.ratingView.setTextColor(ContextCompat.getColor(
                popularChildLayoutBinding.ratingView.context,
                    R.color.info_text_color)
            )
            voteAvg?.roundToInt()?.let { popularChildLayoutBinding.ratingView.setProgress(it) }
            voteAvg.let {
                if (it != null) {
                    if (it > 50) {
                        popularChildLayoutBinding.ratingView.setProgressColor(
                            ContextCompat.getColor(popularChildLayoutBinding.ratingView.context,R.color.colorPrimary)
                        )

                    } else {
                        popularChildLayoutBinding.ratingView.setProgressColor(
                            ContextCompat.getColor(popularChildLayoutBinding.ratingView.context,R.color.title_color)
                        )

                    }
                }
            }

        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: Results?)
    }

    companion object {
        private val USER_COMPARATOR = object : DiffUtil.ItemCallback<Results>() {
            override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean =
                oldItem.original_title == newItem.original_title

            override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean =
                newItem.equals(oldItem)
        }
    }
}