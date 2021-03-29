package com.liviolopez.timehopstories.ui.source

import androidx.lifecycle.*
import com.liviolopez.timehopstories.data.remote.response.ApiSourceResponse
import com.liviolopez.timehopstories.repository.Repository
import com.liviolopez.timehopstories.utils.Resource
import com.liviolopez.timehopstories.utils.extensions.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SourceViewModel @Inject constructor (
    private val repository: Repository
) : ViewModel() {

    private val _sourceInfo: MutableLiveData<Resource<ApiSourceResponse>> by lazy {
        MutableLiveData<Resource<ApiSourceResponse>>()
    }

    val sourceInfo = _sourceInfo.asLiveData()

    fun getSourceItemsList(sourceId: Int) {
        viewModelScope.launch {
            _sourceInfo.value = Resource.loading()

            try {
                _sourceInfo.value = repository.getSourceInfo(sourceId)
            } catch (e: Throwable){
                _sourceInfo.value = Resource.error(e)
            }
        }
    }
}