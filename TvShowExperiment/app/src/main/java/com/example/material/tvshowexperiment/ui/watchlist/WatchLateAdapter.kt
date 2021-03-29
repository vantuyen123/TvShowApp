package com.example.material.tvshowexperiment.ui.watchlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.material.tvshowexperiment.data.TvShow
import com.example.material.tvshowexperiment.databinding.ItemMostPopularBinding
import com.example.material.tvshowexperiment.ui.mostpopular.DiffCallback

class WatchLateAdapter : ListAdapter<TvShow, WatchLateAdapter.WatchLateViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchLateViewHolder {
        return WatchLateViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: WatchLateViewHolder, position: Int) {
        val items = getItem(position)
        if (items != null) {
            holder.bind(items)
        }
    }

    class WatchLateViewHolder(val binding: ItemMostPopularBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(items: TvShow?) {
            binding.imageDelete.visibility = View.VISIBLE
            binding.tvShow = items
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): WatchLateViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMostPopularBinding.inflate(layoutInflater)
                return WatchLateViewHolder(binding)
            }
        }
    }
}