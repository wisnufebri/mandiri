package com.test.wisnufebri.component.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.test.wisnufebri.data.config.GoToVideo
import com.test.wisnufebri.data.model.Video
import com.test.wisnufebri.databinding.ListItemVideoBinding

class VideoListAdapter internal constructor(private val goToVideo: GoToVideo) :
    ListAdapter<(Video), VideoListAdapter.ViewHolder>(VideoDiffCallback()) {

    class ViewHolder(private val binding: ListItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(goToVideo: GoToVideo, item: Video) {
            binding.goToInterface = goToVideo
            binding.video = item
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(goToVideo, getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemVideoBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    private class VideoDiffCallback : DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem.id == newItem.id
        }
    }
}