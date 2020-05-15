package com.alirezamh.android.addresstest.data.network

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Author:      Alireza Mahmoodi
 * Created:     5/13/2020
 * Email:       mahmoodi.dev@gmail.com
 * Website:     alirezamh.com
 */
object ServiceGenerator {

    private val httpLogger = HttpLoggingInterceptor()
    private val retrofitBuilder = Retrofit.Builder()

    fun <T> create(serviceClass: Class<T>, baseURL: String): T {

        httpLogger.level = HttpLoggingInterceptor.Level.BODY

        val okHttpBuilder = getOkHttpBuilder()

        val gson = GsonBuilder().setLenient().create()

        val retrofit = retrofitBuilder
            .client(okHttpBuilder.build())
            .baseUrl(baseURL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(serviceClass)
    }

    private fun getOkHttpBuilder(): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()

        if (httpLogger.level != HttpLoggingInterceptor.Level.NONE) {
            builder.addInterceptor(httpLogger)
        }

        return builder
    }
}

