package com.liviolopez.timehopstories.di

import com.liviolopez.timehopstories.data.local.AppDataBase
import com.liviolopez.timehopstories.data.remote.RemoteDataSource
import com.liviolopez.timehopstories.repository.Repository
import com.liviolopez.timehopstories.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        remoteData: RemoteDataSource,
        localData: AppDataBase
    ): Repository {
        return RepositoryImpl(remoteData, localData)
    }

}