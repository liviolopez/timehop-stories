package com.liviolopez.timehopstories.ui.stories

import androidx.lifecycle.*
import com.liviolopez.timehopstories.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * use in terminal the following command to test SaveStateHandle
 * $> adb shell am kill com.liviolopez.timehopstories
 */

@HiltViewModel
class StoriesViewModel @Inject constructor (
    private val repository: Repository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val KEY_CURRENT_ITEM = "current_item"
    private val KEY_CURRENT_VIDEO_POS = "current_video_position"

    private val _currentItem = savedStateHandle.getLiveData<Int>(KEY_CURRENT_ITEM)
    private val _currentVideoPos = savedStateHandle.getLiveData<Long>(KEY_CURRENT_VIDEO_POS)

    var currentItem : Int?
        get() = _currentItem.value
        set(value) { _currentItem.postValue(value) } // update _currentItem in background threat, it has no active observers

    var currentVideoPos : Long
        get() = _currentVideoPos.value ?: 0L
        set(value) { _currentVideoPos.postValue(value) } // update _currentVideoPos in background threat, it has no active observers

    val latestItemsList = repository.getLatestItems().asLiveData()

    fun itemAt(position: Int) = latestItemsList.value?.data?.get(position)
}