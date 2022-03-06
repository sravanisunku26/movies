package com.lloyds.assignment.custom.ui.main.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lloyds.assignment.R
import com.lloyds.assignment.custom.model.PlayingNowResponse
import com.lloyds.assignment.custom.model.Results
import com.lloyds.assignment.databinding.PlayingNowChildLayoutBinding
import com.squareup.picasso.Picasso

/**
 * Adapter for displaying Playing now list items
 */
class PlayingNowMoviesAdapter(
    private val items: PlayingNowResponse,
    private var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<PlayingNowMoviesAdapter.ViewHolder>() {
    private lateinit var playingNowChildLayoutBinding: PlayingNowChildLayoutBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        playingNowChildLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.playing_now_child_layout,
            parent,
            false
        )
        return ViewHolder(playingNowChildLayoutBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items.results[position], onItemClickListener)

    override fun getItemCount() = items.results.size

    class ViewHolder(private var playingNowChildLayoutBinding: PlayingNowChildLayoutBinding) :
        RecyclerView.ViewHolder(playingNowChildLayoutBinding.root) {

        fun bind(item: Results, onItemClickListener: OnItemClickListener) {
            Picasso.get().load(Uri.parse("https://image.tmdb.org/t/p/original${item.poster_path}"))
                .placeholder(
                    R.drawable.placeholder
                ).fit().centerCrop()
                .into(playingNowChildLayoutBinding.thumbnailIv)
            playingNowChildLayoutBinding.thumbnailIv.setOnClickListener {
                onItemClickListener.onItemClick(
                    item
                )
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: Results?)
    }

}