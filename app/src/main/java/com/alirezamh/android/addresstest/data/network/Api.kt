package com.alirezamh.android.addresstest.data.network

import com.alirezamh.android.addresstest.data.model.ErrorModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import java.io.IOException

/**
 * Author:      Alireza Mahmoodi
 * Created:     5/13/2020
 * Email:       mahmoodi.dev@gmail.com
 * Website:     alirezamh.com
 */
object Api {
    const val ERROR_IO = -1
    const val ERROR_NETWORK = -2
    const val HOST: String = "https://bo.hichestan.org"


    suspend inline fun<reified T: Any> result(service: ()-> Deferred<Response<T>>): ApiResult<T> {
        return try {
            val result = service().await()
            if(result.isSuccessful){
                ApiResult.Success(result.body() as T)
            }else{
                ApiResult.Error(createError(result))
            }
        } catch (e: Exception){
            ApiResult.Error(createError(e))
        }
    }

    fun <T> createError(result: Response<T>): ErrorModel {
        return ErrorModel(
            result.code(),
            "Something went wrong, Please try again.",
            result.errorBody().toString())
    }

    fun createError(exception: Exception): ErrorModel {
        return when (exception) {
            is IOException -> ErrorModel(
                code = ERROR_IO,
                message = "Network error, Please try again.",
                errorDetail = exception.message?: exception.toString()
            )

            else -> ErrorModel(
                code = ERROR_NETWORK,
                message = "Network error, Please try again.",
                errorDetail = exception.message?: exception.toString()
            )
        }
    }
}