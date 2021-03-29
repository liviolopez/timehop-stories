package com.liviolopez.timehopstories.di

import android.app.Application
import androidx.room.Room
import com.liviolopez.timehopstories.base.Constants
import com.liviolopez.timehopstories.data.local.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Singleton
    @Provides
    fun provideDataBase(app: Application): AppDataBase =
        Room.databaseBuilder(app, AppDataBase::class.java, Constants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
}