package com.liviolopez.timehopstories.ui.source

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.liviolopez.timehopstories.R
import com.liviolopez.timehopstories.data.remote.response.ItemDto
import com.liviolopez.timehopstories.databinding.ItemOnSourceBinding
import com.liviolopez.timehopstories.utils.extensions.setImage

class ItemsAdapter : ListAdapter<ItemDto, ItemsAdapter.ItemViewHolder>(ItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_on_source, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ItemDto) {
            val binding = ItemOnSourceBinding.bind(itemView)

            binding.apply {
                ivThumbnail.setImage(item.url)
                tvCopyright.text = item.copyright
                tvSite.text = item.site
            }
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<ItemDto>() {
        override fun areItemsTheSame(oldItem: ItemDto, newItem: ItemDto) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ItemDto, newItem: ItemDto) = oldItem == newItem
    }
}