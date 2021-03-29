package com.liviolopez.timehopstories.data.remote

import com.liviolopez.timehopstories.data.remote.response.ApiItemsResponse
import com.liviolopez.timehopstories.data.remote.response.ApiSourceResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

// https://www.splashbase.co/api/v1/images/latest -> images_only=true || videos_only=true
// https://www.splashbase.co/api/v1/sources/:id

interface ApiService {
    @GET("api/v1/images/latest")
    suspend fun getLatestItems(
        @QueryMap(encoded = true) params: Map<String, String>
    ): ApiItemsResponse

    @GET("api/v1/sources/{sourceId}")
    suspend fun getSourceInfo(
        @Path("sourceId", encoded = true) sourceId: Int,
    ): ApiSourceResponse
}