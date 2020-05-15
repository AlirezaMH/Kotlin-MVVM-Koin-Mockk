package com.alirezamh.android.addresstest.ui.main

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alirezamh.android.addresstest.data.model.PlaceModel
import com.alirezamh.android.addresstest.data.model.PlaceType
import com.alirezamh.android.addresstest.data.network.ApiResult
import com.alirezamh.android.addresstest.data.repository.AddressRepository
import io.mockk.*
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

/**
 * Author:      Alireza Mahmoodi
 * Created:     5/14/2020
 * Email:       mahmoodi.dev@gmail.com
 * Website:     alirezamh.com
 */
@Config(manifest= Config.NONE)
@RunWith(AndroidJUnit4::class)
class MainViewModelTest {
    private val addressRepository: AddressRepository = mockk()

    @SpyK
    private lateinit var viewModel: MainViewModel

    @Rule
    @JvmField
    val ruleForLivaData = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        val application = ApplicationProvider.getApplicationContext<Application>()
        viewModel = spyk(MainViewModel(application, addressRepository), recordPrivateCalls = true)
    }

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `Check updatePlaces on Success response`(){
        runBlocking {
            val expected = listOf(mockk<PlaceModel>())
            coEvery { addressRepository.getPlaces(any(), any()) } returns mockk<ApiResult.Success<Map<PlaceType, List<PlaceModel>>>>{
                every { data } returns mapOf(PlaceType.PARKS to expected)
            }
            viewModel.updatePlaces()
            delay(100)
            coVerify { addressRepository.getPlaces(any(), any()) }
            assertNull(viewModel.error.value)
            assertEquals(expected, viewModel.places.value)
        }
    }

    @Test
    fun `Check updatePlaces on Error response`(){
        runBlocking {
            val expected = "Error"
            assertNull(viewModel.error.value?.message)
            coEvery { addressRepository.getPlaces(any(), any()) } returns mockk<ApiResult.Error>{
                every { error } returns mockk{
                    every { message } returns expected
                }
            }

            viewModel.updatePlaces()
            delay(100)
            coVerify { addressRepository.getPlaces(any(), any()) }
            assertEquals(expected, viewModel.error.value?.message)
        }
    }

    @Test
    fun `Check get placeTypes`() {
        val expected = PlaceType.values()
        assertTrue(expected.contentEquals(viewModel.placeTypes))
    }

    @Test
    fun `Check calling filterItems after setSelectedTabIndex`() {
        viewModel.selectedTabIndex = 1
        verify { viewModel["filterItems"]() }
    }

    @Test
    fun `Check get selectedTab`() {
        PlaceType.values().forEachIndexed { index, placeType ->
            viewModel.selectedTabIndex = index
            assertEquals(placeType, viewModel.selectedTab)
        }
    }

    @Test
    fun `Check get listTitle`() {
        val title = "Title_"
        val index = 0
        val expected = title + PlaceType.values()[index].title
        val id = slot<Int>()
        every { viewModel.context.getString(capture(id)) } answers {title + id.captured}
        viewModel.selectedTabIndex = index
        assertEquals(expected, viewModel.listTitle)
    }

    @Test
    fun `Check loading`(){
        assertFalse(viewModel.loading.value?: false)
        viewModel.updatePlaces()
        assertTrue(viewModel.loading.value?: false)
    }

}
