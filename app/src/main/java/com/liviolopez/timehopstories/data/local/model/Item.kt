package com.liviolopez.timehopstories.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class Item(
    @PrimaryKey val id: Int,
    val url: String,
    val copyright: String?,
    val site: String?,
    val sourceId: Int?,
    val largeUrl: String,
    var isVideo: Boolean,
    val createdAt: Long = System.currentTimeMillis()
)