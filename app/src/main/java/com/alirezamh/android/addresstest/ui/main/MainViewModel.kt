package com.alirezamh.android.addresstest.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alirezamh.android.addresstest.R
import com.alirezamh.android.addresstest.base.BaseViewModel
import com.alirezamh.android.addresstest.data.model.ErrorModel
import com.alirezamh.android.addresstest.data.model.PlaceModel
import com.alirezamh.android.addresstest.data.model.PlaceType
import com.alirezamh.android.addresstest.data.network.ApiResult
import com.alirezamh.android.addresstest.data.repository.AddressRepository
import kotlinx.coroutines.Job

/**
 * Author:      Alireza Mahmoodi
 * Created:     5/13/2020
 * Email:       mahmoodi.dev@gmail.com
 * Website:     alirezamh.com
 */
class MainViewModel(application: Application, private val repo: AddressRepository) : BaseViewModel(application) {
    private var updateJob: Job? = null
    private var totalPlaces: Map<PlaceType, List<PlaceModel>?>? = null
        set(value) {
            field = value
            filterItems()
        }

    private val _places = MutableLiveData<List<PlaceModel>>()
    val places: LiveData<List<PlaceModel>> = _places

    private val _error = MutableLiveData<ErrorModel>()
    val error: LiveData<ErrorModel> get() = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    var lat: Double = 35.7888021
        private set
    var lng: Double = 51.420547
        private set

    val cameraZoom = 15F

    val placeTypes = PlaceType.values()

    var selectedTabIndex: Int = 0
        set(value) {
            field = value
            filterItems()
        }
    val selectedTab: PlaceType get() = placeTypes[selectedTabIndex]

    val listTitle: String get() {
        val title = context.getString(selectedTab.title)
        if(selectedTab == PlaceType.All) return title
        return context.getString(R.string.plural_x, title)
    }

    val lisDescription: String get() {
        val title = if(selectedTab == PlaceType.All) context.getString(R.string.item) else context.getString(selectedTab.title)
        val count = places.value?.size?: 0
        return context.getString(R.string.x_item_founded, count, title)
    }

    private fun filterItems(){
        if(totalPlaces == null){
            _places.postValue(listOf())
            return
        }

        if(selectedTab != PlaceType.All){
            _places.postValue(totalPlaces?.get(selectedTab)?: listOf())
            return
        }

        val items = arrayListOf<PlaceModel>()
        totalPlaces?.forEach {
            it.value?.apply{
                items.addAll(this)
            }
        }
        _places.postValue(items)
//        _places.value = items

    }

    fun updatePlaces(lat: Double = this.lat, lng: Double = this.lng){
        this.lat = lat
        this.lng = lng
        _loading.postValue(true)
        updateJob?.cancel()
        updateJob = launch{
            val result = repo.getPlaces(lat, lng)
            when(result){
                is ApiResult.Success -> totalPlaces = result.data
                is ApiResult.Error -> _error.postValue(result.error)
            }
            _loading.postValue(false)
        }
    }





}