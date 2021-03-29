package com.liviolopez.timehopstories.ui.stories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.liviolopez.timehopstories.R
import com.liviolopez.timehopstories.data.local.model.Item
import com.liviolopez.timehopstories.databinding.ItemOnStoryBinding
import com.liviolopez.timehopstories.utils.extensions.setBlurryImage
import com.liviolopez.timehopstories.utils.extensions.setGone
import com.liviolopez.timehopstories.utils.extensions.setImage
import com.liviolopez.timehopstories.utils.extensions.setVisible

class StoriesAdapter(
    private val onStoryClickListener: OnItemClickListener,
) : ListAdapter<Item, StoriesAdapter.StoryViewHolder>(ItemComparator()) {

    interface OnItemClickListener {
        fun onClickView(view: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesAdapter.StoryViewHolder {
        return StoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_on_story, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class StoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        fun bind(item: Item) {
            val binding = ItemOnStoryBinding.bind(itemView)

            binding.apply {
                if(item.isVideo){
                    ivItemPicture.setGone()
                    vvItemVideo.setVisible()
                } else {
                    ivItemPicture.setImage(item.url)

                    ivItemPicture.setVisible()
                    vvItemVideo.setGone()
                }

                ivItemBackground.setBlurryImage(item.url)

                item.sourceId?.let {
                    fabMoreFromSource.setVisible()
                } ?: run {
                    fabMoreFromSource.setGone()
                }
            }

            binding.root.setOnClickListener(this)
            binding.fabMoreFromSource.setOnClickListener(this)
            binding.btNext.setOnClickListener(this)
            binding.btPrevious.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            onStoryClickListener.onClickView(v, super.getAdapterPosition())
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Item, newItem: Item) = oldItem == newItem
    }
}