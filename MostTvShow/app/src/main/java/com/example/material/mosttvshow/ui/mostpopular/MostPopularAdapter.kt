package com.example.material.mosttvshow.ui.mostpopular

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.material.mosttvshow.databinding.ItemMostPopularBinding
import com.example.material.mosttvshow.domain.TvShow

class MostPopularAdapter :
    ListAdapter<TvShow, MostPopularAdapter.MostPopularViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostPopularViewHolder {
        return MostPopularViewHolder.form(parent)
    }


    override fun onBindViewHolder(holder: MostPopularViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    class MostPopularViewHolder(private val binding: ItemMostPopularBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: TvShow) {
            Log.d("Tag","line 33")
            binding.also {
                it.tvShow = item
                it.executePendingBindings()
            }
        }
        companion object{
            fun form(parent: ViewGroup): MostPopularViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMostPopularBinding.inflate(layoutInflater)
                return MostPopularViewHolder(binding)
            }

        }
    }

}
class DiffCallback : DiffUtil.ItemCallback<TvShow>() {
    override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
        Log.d("Adapter", "Old: ${oldItem.id} New: ${newItem.id}")
        Log.d("Adapter", "Equal: ${oldItem.id == newItem.id}")
        return newItem == oldItem
    }

    override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
        return newItem.id == oldItem.id
    }
}