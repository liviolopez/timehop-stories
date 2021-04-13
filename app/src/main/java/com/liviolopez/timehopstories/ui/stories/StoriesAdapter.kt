package com.liviolopez.timehopstories.ui.stories

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.liviolopez.timehopstories.data.local.model.Item
import com.liviolopez.timehopstories.databinding.ItemOnStoryImageBinding
import com.liviolopez.timehopstories.databinding.ItemOnStoryVideoBinding
import com.liviolopez.timehopstories.ui._components.BindingViewHolder
import com.liviolopez.timehopstories.ui._components.typed
import com.liviolopez.timehopstories.ui._components.viewHolderFrom
import com.liviolopez.timehopstories.utils.extensions.*

class StoriesAdapter(
    private val onStoryClickListener: OnItemClickListener,
) : ListAdapter<Item, BindingViewHolder<*>>(ItemComparator()) {

    interface OnItemClickListener {
        fun onClickView(view: View, position: Int)
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isVideo) 1 else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<*> {
        return when(viewType){
            1    -> parent.viewHolderFrom(ItemOnStoryVideoBinding::inflate)
            else -> parent.viewHolderFrom(ItemOnStoryImageBinding::inflate)
        }
    }

    override fun onBindViewHolder(holder: BindingViewHolder<*>, position: Int) {
        val item = getItem(position)
        when(item.isVideo){
            true -> holder.typed<ItemOnStoryVideoBinding>().bind(item, position)
            false -> holder.typed<ItemOnStoryImageBinding>().bind(item, position)
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Item, newItem: Item) = oldItem == newItem
    }

    private fun onClick(position: Int, vararg views: View) {
        views.forEach { view ->
            view.setOnClickListener { onStoryClickListener.onClickView(view, position) }
        }
    }

    // -------------- BindingViewHolder Extensions ---------------

    @JvmName("bindItemOnStoryImageBinding")
    private fun BindingViewHolder<ItemOnStoryImageBinding>.bind(item: Item, position: Int) {
        binding.apply {
            ivItemPicture.setImage(item.url)
            ivItemBackground.setBlurryImage(item.url)
            fabMoreFromSource.visibleIf { item.sourceId !== null }

            onClick(position, root, fabMoreFromSource, btNext, btPrevious)
        }
    }

    private fun BindingViewHolder<ItemOnStoryVideoBinding>.bind(item: Item, position: Int) {
        binding.apply {
            ivItemBackground.setBlurryImage(item.url)
            fabMoreFromSource.visibleIf { item.sourceId !== null }

            onClick(position, root, fabMoreFromSource, btNext, btPrevious)
        }
    }
}
