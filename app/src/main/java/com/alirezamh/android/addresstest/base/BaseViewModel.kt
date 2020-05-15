package com.alirezamh.android.addresstest.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.alirezamh.android.addresstest.App
import com.alirezamh.android.addresstest.util.Coroutine

/**
 * Author:      Alireza Mahmoodi
 * Created:     5/13/2020
 * Email:       mahmoodi.dev@gmail.com
 * Website:     alirezamh.com
 */
abstract class BaseViewModel(application: Application) : AndroidViewModel(application){
    val context: Context get() = getApplication<App>().applicationContext

    protected fun <T: Any> launch(work: suspend (() -> T?)) = Coroutine.main(work)

    protected fun <T: Any> launch(work: suspend (() -> T?), callback: ((T?) -> Unit)? = null) = Coroutine.ioThenMain(work, callback)

}