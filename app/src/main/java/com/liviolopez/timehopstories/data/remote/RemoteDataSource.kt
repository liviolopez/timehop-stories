package com.liviolopez.timehopstories.data.remote

import com.liviolopez.timehopstories.data.remote.response.ApiSourceResponse
import com.liviolopez.timehopstories.data.remote.response.ItemDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun fetchLatestItems(): List<ItemDto> {
        return apiService.getLatestItems(mapOf()).items // mapOf() if for filter only videos or only images
    }

    suspend fun fetchSourceInfo(sourceId: Int): ApiSourceResponse {
        return apiService.getSourceInfo(sourceId)
    }
}