package com.alirezamh.android.addresstest

import com.alirezamh.android.addresstest.data.repository.AddressRepository
import com.alirezamh.android.addresstest.data.repository.AddressRepositoryImpl
import com.alirezamh.android.addresstest.ui.main.MainFragment
import com.alirezamh.android.addresstest.ui.main.MainViewModel
import com.alirezamh.android.addresstest.ui.main.PlacesListAdapter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Author:      Alireza Mahmoodi
 * Created:     5/13/2020
 * Email:       mahmoodi.dev@gmail.com
 * Website:     alirezamh.com
 */
object Dependencies {

    private val repositories = module {
        single<AddressRepository>(createdAtStart = true) { AddressRepositoryImpl() }
    }

    private val mainPage = module {
        viewModel { MainViewModel(get(), get()) }
        factory { MainFragment() }
        factory { PlacesListAdapter() }
    }

    val modules = listOf(
        repositories,
        mainPage
    )
}