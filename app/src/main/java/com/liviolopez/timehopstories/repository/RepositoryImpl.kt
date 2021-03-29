package com.liviolopez.timehopstories.repository

import androidx.room.withTransaction
import com.liviolopez.timehopstories.data.local.AppDataBase
import com.liviolopez.timehopstories.data.remote.RemoteDataSource
import com.liviolopez.timehopstories.data.remote.response.ApiSourceResponse
import com.liviolopez.timehopstories.data.remote.response.toLocalModel
import com.liviolopez.timehopstories.utils.Resource
import com.liviolopez.timehopstories.utils.networkBoundResource
import java.util.concurrent.TimeUnit

class RepositoryImpl(
    private val remoteData: RemoteDataSource,
    private val localData: AppDataBase
) : Repository {
    private val itemDao = localData.itemDao()

    override fun getLatestItems() = networkBoundResource(
        loadFromDb = {
            itemDao.getAllItems()
        },
        createCall = {
            remoteData.fetchLatestItems()
        },
        saveFetchResult = { items ->
            localData.withTransaction {
                itemDao.deleteAllItems()
                itemDao.insertItems(items.map { it.toLocalModel() })
            }
        },
        shouldFetch = { items ->
            // Refresh data from the API only after 5 minutes or when list of items is empty
            items.minByOrNull { it.createdAt }?.let {
                it.createdAt < System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(5)
            } ?:run { true }
        }
    )

    override suspend fun getSourceInfo(sourceId: Int): Resource<ApiSourceResponse> {
        return Resource.success(remoteData.fetchSourceInfo(sourceId))
    }
}