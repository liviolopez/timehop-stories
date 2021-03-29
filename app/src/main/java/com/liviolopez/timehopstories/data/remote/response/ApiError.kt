package com.liviolopez.timehopstories.data.remote.response

import com.google.gson.Gson
import retrofit2.HttpException
import java.lang.Exception

object ApiError {
    fun mapResponseCauses(error: Throwable) : List<ApiErrorBody>? {
        return when(error){
            is HttpException -> getCauses(error)
            else -> null
        }
    }

    private fun getCauses(error: HttpException) : List<ApiErrorBody>? {
        return try {
            error.response()?.errorBody()?.let {
                listOf(Gson().fromJson(it.charStream(), ApiErrorBody::class.java))
            }
        } catch (e: Exception) {
            null
        }
    }

    data class ApiErrorBody(
        val message: String
    )
}