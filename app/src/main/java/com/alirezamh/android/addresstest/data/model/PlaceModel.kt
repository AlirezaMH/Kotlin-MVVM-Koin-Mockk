package com.alirezamh.android.addresstest.data.model

/**
 * Author:      Alireza Mahmoodi
 * Created:     5/13/2020
 * Email:       mahmoodi.dev@gmail.com
 * Website:     alirezamh.com
 */
data class PlaceModel (
    val title: String,
    val lat: Double,
    val lng: Double,
    val distance: Double?,
    val duration: Double?
)