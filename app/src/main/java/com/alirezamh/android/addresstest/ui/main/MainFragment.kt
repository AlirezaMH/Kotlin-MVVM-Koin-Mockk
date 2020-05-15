package com.alirezamh.android.addresstest.ui.main

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alirezamh.android.addresstest.R
import com.alirezamh.android.addresstest.base.BaseFragment
import com.alirezamh.android.addresstest.data.model.PlaceType
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment_bottom_sheet.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Author:      Alireza Mahmoodi
 * Created:     5/13/2020
 * Email:       mahmoodi.dev@gmail.com
 * Website:     alirezamh.com
 */
class MainFragment: BaseFragment() {
    private val viewModel by viewModel<MainViewModel>()
    private val placesListAdapter by inject<PlacesListAdapter>()
    private lateinit var map: GoogleMap
    private var moveEndHandler: Handler? = Handler()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.error.observe(viewLifecycleOwner, Observer {
            if(isAdded && it.message != null) Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
        })

        viewModel.updatePlaces()
        initTabs()
        initBottomSheet()
        initMap()
    }

    private fun initTabs(){
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewModel.selectedTabIndex = tabs.selectedTabPosition
            }
        })

        viewModel.placeTypes.forEach {
            tabs.newTab().apply {
                this.view.rotationY = 180F
                setText(it.title)
                tabs.addTab(this, it == PlaceType.All)
            }
        }
    }

    private fun initBottomSheet(){
        bottomSheetList.adapter = placesListAdapter
        bottomSheetList.layoutManager = LinearLayoutManager(context)

        viewModel.places.observe(viewLifecycleOwner, Observer {
            bottomSheetTitle.text = viewModel.listTitle
            bottomSheetDesc.text = viewModel.lisDescription
            placesListAdapter.setItems(it)
            updateMarkers()
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            progressBar.visibility = if(it) View.VISIBLE else View.INVISIBLE
        })
    }

    private fun initMap(){
        with(mapView){
            onCreate(null)
            getMapAsync {
                map = it
                MapsInitializer.initialize(context)
                it.mapType = GoogleMap.MAP_TYPE_NORMAL
                it.setOnCameraMoveListener {
                    moveEndHandler?.removeCallbacksAndMessages(null)
                    moveEndHandler?.postDelayed({ onMapMoved(map.cameraPosition.target) }, 300)
                }
                moveCamera()
                updateMarkers()
            }
        }

    }

    private fun moveCamera() {
        if(!::map.isInitialized) return

        val cameraPosition = CameraPosition.builder(map.cameraPosition)
            .zoom(viewModel.cameraZoom)
            .target(LatLng(viewModel.lat, viewModel.lng))
            .build()
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        map.moveCamera(cameraUpdate)
    }

    private fun updateMarkers(){
        if(!::map.isInitialized) return

        map.clear()
        viewModel.places.value?.forEach {
            map.addMarker(
                MarkerOptions()
                    .position(LatLng(it.lat, it.lng))
                    .title(it.title)
            )
        }
    }

    private fun onMapMoved(latLng: LatLng){
        viewModel.updatePlaces(latLng.latitude, latLng.longitude)
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }
}