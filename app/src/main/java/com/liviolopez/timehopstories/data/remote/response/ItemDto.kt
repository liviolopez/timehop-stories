package com.liviolopez.timehopstories.data.remote.response

import com.liviolopez.timehopstories.data.local.model.Item
import com.liviolopez.timehopstories.utils.StringUtils
import kotlin.reflect.full.memberProperties

data class ItemDto(
    val id: Int,
    val url: String,
    val copyright: String?,
    val site: String?,
    val sourceId: Int?,
    val largeUrl: String
)

// Mapper with Kotlin Extension (Better performance in comparison of Kotlin Reflect)
fun ItemDto.toLocalModel() = Item(
    id = id,
    url = url,
    copyright = copyright,
    site = site,
    sourceId = sourceId,
    largeUrl = largeUrl,
    isVideo = StringUtils.isVideoUrl(largeUrl)
)

// Mapper with Kotlin Reflect (This code was added here as a sample)
// can be used when the class to be mapped has a large number of params
// fun ItemDto.toLocalModel() = with(::Item) {
//    val propertiesByName = ItemDto::class.memberProperties.associateBy { it.name }
//
//    callBy(args = parameters.associateWith { parameter ->
//        when (parameter.name) {
//            "isVideo" -> StringUtils.isVideoUrl(largeUrl)
//            else -> propertiesByName[parameter.name]?.get(this@toLocalModel)
//        }
//    })
// }