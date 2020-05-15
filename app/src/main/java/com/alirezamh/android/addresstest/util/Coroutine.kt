package com.alirezamh.android.addresstest.util

import kotlinx.coroutines.*

/**
 * Author:      Alireza Mahmoudi
 * Created:     2/26/19
 * Email:       mahmoodi.dev@gmail.com
 * Website:     alirezamh.com
 */
object Coroutine {

    fun <T: Any> io(work: suspend (() -> T?)): Job =
        CoroutineScope(Dispatchers.IO).launch {
            work()
        }

    fun <T: Any> ioThenMain(work: suspend (() -> T?), callback: ((T?) -> Unit)? = null): Job =
        main {
            callback?.invoke(async(work))
        }

    fun <T: Any> main(work: suspend (() -> T?)): Job =
        CoroutineScope(Dispatchers.Main).launch {
            work()
        }

    fun <T : Any> defaultDispatcherAsync(work: suspend (() -> T)): Deferred<T> =
        CoroutineScope(Dispatchers.Default).async {
            work()
        }

    suspend fun <T: Any> async(work: suspend (() -> T?)): T? =
        CoroutineScope(Dispatchers.IO).async {
            work()
        }.await()
}