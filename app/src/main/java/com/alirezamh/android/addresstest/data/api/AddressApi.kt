package com.alirezamh.android.addresstest.data.api

import com.alirezamh.android.addresstest.data.network.Api
import com.alirezamh.android.addresstest.data.network.ServiceGenerator
import com.alirezamh.android.addresstest.data.service.AddressService

/**
 * Author:      Alireza Mahmoodi
 * Created:     5/13/2020
 * Email:       mahmoodi.dev@gmail.com
 * Website:     alirezamh.com
 */
object AddressApi {
    private val service = ServiceGenerator.create(AddressService::class.java, Api.HOST)


    /**
     * Get Places
     */
    suspend fun getPlaces(lat: Double, lng: Double) = Api.result {
        service.getPlaces(lat, lng)
    }
}