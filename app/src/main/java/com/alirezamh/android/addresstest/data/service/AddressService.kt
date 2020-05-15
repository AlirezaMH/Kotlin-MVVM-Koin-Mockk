package com.alirezamh.android.addresstest.data.service

import com.alirezamh.android.addresstest.data.model.PlaceModel
import com.alirezamh.android.addresstest.data.model.PlaceType
import com.alirezamh.android.addresstest.data.network.ApiResult
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Author:      Alireza Mahmoodi
 * Created:     5/13/2020
 * Email:       mahmoodi.dev@gmail.com
 * Website:     alirezamh.com
 */
interface AddressService {

    @GET("/rest/hmj/by/point/{lat}/{lng}")
    fun getPlaces(
        @Path("lat") lat: Double,
        @Path("lng") lng: Double

    ): Deferred<Response<Map<PlaceType, List<PlaceModel>>>>

}