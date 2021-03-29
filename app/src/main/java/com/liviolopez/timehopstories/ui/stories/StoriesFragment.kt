package com.liviolopez.timehopstories.ui.stories

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.liviolopez.timehopstories.R
import com.liviolopez.timehopstories.data.local.model.Item
import com.liviolopez.timehopstories.databinding.FragmentStoriesBinding
import com.liviolopez.timehopstories.databinding.ItemOnStoryBinding
import com.liviolopez.timehopstories.ui._components.*
import com.liviolopez.timehopstories.utils.Resource
import com.liviolopez.timehopstories.utils.extensions.setGone
import com.liviolopez.timehopstories.utils.extensions.setVisible
import com.liviolopez.timehopstories.utils.extensions.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoriesFragment : Fragment(R.layout.fragment_stories), StoriesAdapter.OnItemClickListener {
    private val TAG = "StoriesFragment"

    private val viewModel: StoriesViewModel by activityViewModels()

    private lateinit var binding: FragmentStoriesBinding
    private lateinit var storiesAdapter: StoriesAdapter

    lateinit var appPlayer: AppPlayer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentStoriesBinding.bind(view)
        (activity as AppCompatActivity).supportActionBar?.hide()

        storiesAdapter = StoriesAdapter(this)
        binding.vpStory.isUserInputEnabled = false
        binding.vpStory.adapter = storiesAdapter

        appPlayer = AppPlayer(requireContext())
        setupStoriesAdapter()

        binding.vpStory.registerOnPageChangeCallback(viewPageChangeCallback)
    }

    private fun setupStoriesAdapter() {
        viewModel.latestItemsList.observe(viewLifecycleOwner, { result ->

            when (result.status) {
                Resource.Status.LOADING -> {
                    if(result.data.isNullOrEmpty()) {
                        binding.standbyView.loading
                    } else {
                        submitListStoriesAdapter(result.data)
                    }
                }
                Resource.Status.SUCCESS -> {
                    if(appPlayer.currentPlayerView == null || appPlayer.currentPlayerView?.player?.isPlaying == true) {
                        binding.standbyView.success

                        (result.data as List<Item>).let { items ->
                            if (items.isNotEmpty()) {
                                binding.vpStory.setVisible()
                                submitListStoriesAdapter(items)
                            } else {
                                binding.apply { standbyView showEmptyMsg vpStory }
                            }
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    Log.e(TAG, "Error: ${result.throwable?.localizedMessage}")

                    if(result.data.isNullOrEmpty()) {
                        binding.vpStory.setGone()
                        binding.standbyView.showError = getString(R.string.error_msg_param, result.throwable?.localizedMessage)
                    } else {
                        submitListStoriesAdapter(result.data)
                        binding.root.showSnackBar(getString(R.string.error_msg_updating))
                    }
                }
            }
        })
    }

    private fun submitListStoriesAdapter(itemsList: List<Item>) {
        storiesAdapter.submitList(itemsList)

        viewModel.currentItem?.let {
            binding.vpStory.setCurrentItem(it, false)
        }
    }

    override fun onClickView(view: View, position: Int) {
        when (view.id) {
            R.id.bt_previous -> binding.vpStory.currentItem = position - 1
            R.id.bt_next -> binding.vpStory.currentItem = position + 1
            R.id.fab_more_from_source -> openMoreFromSource(viewModel.itemAt(position)!!.sourceId!!)
            else -> Unit
        }

        appPlayer.cleanView()

        viewModel.currentVideoPos = 0
        viewModel.currentItem = binding.vpStory.currentItem
    }

    private val viewPageChangeCallback = object : ViewPager2.OnPageChangeCallback(), AppPlayer.OnProgressListener {
        var newPage = false

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            appPlayer.onProgressListener = null

            (binding.vpStory[0] as RecyclerView).findViewHolderForAdapterPosition(position)?.let {
                val itemStory = ItemOnStoryBinding.bind(it.itemView)

                if (viewModel.itemAt(position)!!.isVideo) {
                    val progressListener = this

                    appPlayer.apply {
                        setTrackAndPlay(itemStory.vvItemVideo, viewModel.itemAt(position)!!.largeUrl, viewModel.currentVideoPos)
                        onProgressListener = progressListener
                        loadingBar = itemStory.loadingVideo
                    }
                }
            }
        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            newPage = true
        }

        override fun onUpdatePosition(currentPosition: Long) {
            viewModel.currentVideoPos = currentPosition
        }
    }

    private fun openMoreFromSource(sourceId: Int) {
        findNavController(this).navigate(
            StoriesFragmentDirections.actionStoriesFragmentToSourceFragment(
                sourceId = sourceId
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        appPlayer.release()
    }

    override fun onPause() {
        super.onPause()
        appPlayer.pause()
    }

    override fun onResume() {
        super.onResume()
        appPlayer.resume()
    }
}