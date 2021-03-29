package com.liviolopez.timehopstories.ui.source

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.liviolopez.timehopstories.R
import com.liviolopez.timehopstories.data.remote.response.ApiError
import com.liviolopez.timehopstories.databinding.FragmentSourceBinding
import com.liviolopez.timehopstories.ui._components.*
import com.liviolopez.timehopstories.utils.Resource
import com.liviolopez.timehopstories.utils.extensions.setGone
import com.liviolopez.timehopstories.utils.extensions.setVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SourceFragment : Fragment(R.layout.fragment_source) {
    private val TAG = "SourceFragment"

    private val args: SourceFragmentArgs by navArgs()

    private val viewModel: SourceViewModel by activityViewModels()

    private lateinit var binding: FragmentSourceBinding
    private lateinit var itemsAdapter: ItemsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSourceBinding.bind(view)
        (activity as AppCompatActivity).supportActionBar?.show()

        itemsAdapter = ItemsAdapter()
        binding.rvSourceItems.adapter = itemsAdapter

        setupSourceItemsAdapter()
        viewModel.getSourceItemsList(args.sourceId)
    }

    private fun setupSourceItemsAdapter() {
        viewModel.sourceInfo.observe(viewLifecycleOwner, { result ->

            when (result.status) {
                Resource.Status.LOADING -> binding.standbyView.loading
                Resource.Status.SUCCESS -> {
                    binding.standbyView.success

                    (activity as AppCompatActivity).supportActionBar?.title = result.data?.name

                    binding.tvUrl.text = result.data?.url
                    binding.tvItemsCount.text = getString(R.string.total_items, result.data?.itemsCount.toString())

                    result.data?.items?.let { items ->
                        if (items.isNotEmpty()) {
                            binding.rvSourceItems.setVisible()
                            itemsAdapter.submitList(items)
                        } else {
                            binding.apply { standbyView showEmptyMsg rvSourceItems }
                        }
                    }

                }
                Resource.Status.ERROR -> {
                    itemsAdapter.submitList(emptyList())
                    binding.rvSourceItems.setGone()

                    binding.standbyView.showError = getString(R.string.error_msg_param, result.throwable!!.localizedMessage)

                    // ---------------- LOG -----------------
                    Log.e(TAG, "Error: ${result.throwable.message}")

                    // Sample to map custom API errors
                    ApiError.mapResponseCauses(result.throwable)?.forEach {
                        Log.e(TAG, "ApiError: ${it.message}")
                    }
                }
            }
        })
    }
}