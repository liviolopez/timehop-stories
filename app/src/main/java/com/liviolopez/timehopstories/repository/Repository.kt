package com.liviolopez.timehopstories.repository

import com.liviolopez.timehopstories.data.local.model.Item
import com.liviolopez.timehopstories.data.remote.response.ApiSourceResponse
import com.liviolopez.timehopstories.utils.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getLatestItems(): Flow<Resource<List<Item>>>
    suspend fun getSourceInfo(sourceId: Int): Resource<ApiSourceResponse>
}