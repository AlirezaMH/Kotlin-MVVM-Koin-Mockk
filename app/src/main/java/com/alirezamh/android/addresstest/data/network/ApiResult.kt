package com.alirezamh.android.addresstest.data.network

import com.alirezamh.android.addresstest.data.model.ErrorModel

/**
 * Author:      Alireza Mahmoodi
 * Created:     5/13/2020
 * Email:       mahmoodi.dev@gmail.com
 * Website:     alirezamh.com
 */
sealed class ApiResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : ApiResult<T>()
    data class Error(val error: ErrorModel) : ApiResult<Nothing>()
}