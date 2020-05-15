package com.alirezamh.android.addresstest.data.model

import androidx.annotation.StringRes
import com.alirezamh.android.addresstest.R
import com.google.gson.annotations.SerializedName

/**
 * Author:      Alireza Mahmoodi
 * Created:     5/13/2020
 * Email:       mahmoodi.dev@gmail.com
 * Website:     alirezamh.com
 */

enum class PlaceType(@StringRes val title: Int) {
    @SerializedName("all")
    All(R.string.all),
    @SerializedName("cafes")
    CAFES(R.string.cafe),
    @SerializedName("restaurants")
    RESTAURANTS(R.string.restaurant),
    @SerializedName("parks")
    PARKS(R.string.park),
    @SerializedName("shopping_malls")
    SHOPPING_MALLS(R.string.shopping_mall),
    @SerializedName("mosques")
    MOSQUES(R.string.mosque),
    @SerializedName("pharmacies")
    PHARMACIES(R.string.pharmacy),
    @SerializedName("hospitals")
    HOSPITALS(R.string.hospital),
    @SerializedName("schools")
    SCHOOLS(R.string.school),
    @SerializedName("gyms")
    GYMS(R.string.gym),
    @SerializedName("bookstores")
    BOOKSTORES(R.string.bookstore),
    @SerializedName("flower_shops")
    FLOWER_SHOPS(R.string.flower_shop),
    @SerializedName("libraries")
    LIBRARIES(R.string.library),
    @SerializedName("hotels")
    HOTELS(R.string.hotel),
    @SerializedName("parkings")
    PARKINGS(R.string.parking),
    @SerializedName("highways")
    HIGHWAYS(R.string.highway),
    @SerializedName("bus_stops")
    BUS_STOPS(R.string.bus_stop)
}