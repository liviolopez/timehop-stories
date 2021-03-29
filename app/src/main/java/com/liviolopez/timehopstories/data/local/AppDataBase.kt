package com.liviolopez.timehopstories.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.liviolopez.timehopstories.data.local.model.Item
import com.liviolopez.timehopstories.data.local.model.ItemDao

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}