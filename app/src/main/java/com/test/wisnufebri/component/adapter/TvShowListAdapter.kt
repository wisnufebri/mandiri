package com.test.wisnufebri.component.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.test.wisnufebri.data.config.GoToTvShow
import com.test.wisnufebri.data.config.InfiniteContentScrollListener
import com.test.wisnufebri.data.model.TvShow
import com.test.wisnufebri.databinding.ListItemTvShowBinding
import com.test.wisnufebri.databinding.ListItemTvShowGridBinding

class TvShowListAdapter internal constructor(
    private val goToTvShow: GoToTvShow,
    private val infiniteContentScrollListener: InfiniteContentScrollListener
) : ListAdapter<(TvShow), TvShowListAdapter.ViewHolder>(TvShowDiffCallback()) {

    private var isTvShowItemGrid: Boolean = false

    class ViewHolder(private val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(goToTvShow: GoToTvShow, item: TvShow) {
            when (binding) {
                is ListItemTvShowBinding -> {
                    binding.goToInterface = goToTvShow
                    binding.tvShow = item
                    binding.executePendingBindings()
                }
                is ListItemTvShowGridBinding -> {
                    binding.goToInterface = goToTvShow
                    binding.tvShow = item
                    binding.executePendingBindings()
                }
                else -> throw Exception("Invalid list binding")
            }
        }
    }

    override fun submitList(list: List<TvShow>?) {
        val newList: MutableList<TvShow> = arrayListOf()
        if (list != null) newList.addAll(list)
        super.submitList(newList)
        infiniteContentScrollListener.itemsLoaded()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(goToTvShow, getItem(position))
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        if (recyclerView.layoutManager is GridLayoutManager) isTvShowItemGrid = true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = if (isTvShowItemGrid) {
            ListItemTvShowGridBinding.inflate(layoutInflater, parent, false)
        } else {
            ListItemTvShowBinding.inflate(layoutInflater, parent, false)
        }
        return ViewHolder(binding)
    }

    private class TvShowDiffCallback : DiffUtil.ItemCallback<TvShow>() {
        override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
            return oldItem.id == newItem.id
        }
    }
}