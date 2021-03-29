package com.liviolopez.timehopstories.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.liviolopez.timehopstories.base.Constants
import com.liviolopez.timehopstories.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    @Singleton
    @Provides
    fun provideOkHttpClient() = OkHttpClient.Builder()
        .hostnameVerifier { hostname, session -> true } // <- This API has an invalid SSL or an unverified HostName, so the verification is forced to return true to avoid the SSL exception
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
        .build()

    @Singleton
    @Provides
    fun provideApiService(okHttpClient: OkHttpClient, baseUrl: String): ApiService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create()
                )
            )
            .build()
            .create(ApiService::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RemoteParamsModule {

    @Singleton
    @Provides
    fun provideBaseUrl(): String {
        return Constants.BASE_URL
    }

}