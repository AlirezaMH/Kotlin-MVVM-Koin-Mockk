package com.alirezamh.android.addresstest.data.repository

import com.alirezamh.android.addresstest.data.api.AddressApi
import com.alirezamh.android.addresstest.data.model.PlaceModel
import com.alirezamh.android.addresstest.data.model.PlaceType
import com.alirezamh.android.addresstest.data.network.ApiResult

/**
 * Author:      Alireza Mahmoodi
 * Created:     5/13/2020
 * Email:       mahmoodi.dev@gmail.com
 * Website:     alirezamh.com
 */
class AddressRepositoryImpl: AddressRepository {


    override suspend fun getPlaces(lat: Double, lng: Double): ApiResult<Map<PlaceType, List<PlaceModel>>> {
        return AddressApi.getPlaces(lat, lng)
    }
}