package com.liviolopez.timehopstories.data.remote.response

import com.google.gson.annotations.SerializedName

data class ApiItemsResponse(
    // the name of this field "images" is changed to "item" to make it more generic because the API returns videos as well
    @SerializedName("images") val items: List<ItemDto> = emptyList()
)

data class ApiSourceResponse(
    val id: Int,
    val name: String,
    val url: String,

    @SerializedName("images") val items: List<ItemDto> = emptyList(),
    @SerializedName("image_count") val itemsCount: Int = 0,
)