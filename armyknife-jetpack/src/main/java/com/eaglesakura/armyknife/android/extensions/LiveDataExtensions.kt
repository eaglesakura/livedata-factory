@file:Suppress("unused")

package com.eaglesakura.armyknife.android.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.eaglesakura.armyknife.runtime.extensions.send
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * LiveData force active.
 * when owner on destroy, then LiveData will be inactive.
 */
fun <T> LiveData<T>.forceActiveAlive(owner: LifecycleOwner) {
    observeAlive(owner, Observer { /* drop value. */ })
}

/**
 * Observe data when Lifecycle alive.
 * This method call observe always(Example, Activity/Fragment paused and more).
 * If observer should handle data every time and always, May use this method.
 *
 * e.g.)
 * fun onCreate() {
 *      exampleValue.observeAlive(this) { value ->
 *          // do something,
 *          // this message receive on pausing.
 *      }
 *      exampleValue.observe(this) { value ->
 *          // do something,
 *          // this message "NOT" receive on pausing.
 *      }
 * }
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
fun <T> LiveData<T>.observeAlive(owner: LifecycleOwner, observer: Observer<T>) {
    observeForever(observer)
    owner.lifecycle.subscribe {
        if (it == Lifecycle.Event.ON_DESTROY) {
            removeObserver(observer)
        }
    }
}

/**
 * Await receive a data in Coroutines.
 *
 * e.g.)
 * val liveData: LiveData<String> = ...
 * // await LiveData's data
 * val url = liveData.await()
 *
 * e.g.)
 * val liveData: LiveData<String> = ...
 * // await with filter
 * val url = liveData.await { it.startsWith("http") }
 *
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
suspend fun <T> LiveData<T>.await(filter: (value: T) -> Boolean = { true }): T {
    // check initial value.
    value?.also {
        if (filter(it)) {
            return it
        }
    }

    val channel = Channel<T>()
    val observer = Observer<T> {
        val newValue = it ?: return@Observer
        if (filter(newValue)) {
            channel.send(Dispatchers.Main, newValue)
        }
    }
    try {
        observeForever(observer)
        return channel.receive()
    } finally {
        removeObserver(observer)
    }
}

/**
 * Await receive a data in Coroutines.
 *
 * e.g.)
 * val liveData: LiveData<String> = ...
 * // await LiveData's data
 * val url = liveData.await()
 *
 * e.g.)
 * val liveData: LiveData<String> = ...
 * // await with filter
 * val url = liveData.await { it.startsWith("http") }
 *
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
suspend fun <T> LiveData<T>.await(
    checkInitialValue: Boolean = true,
    filter: (value: T) -> Boolean = { true }
): T {
    // check initial value.
    if (checkInitialValue) {
        value?.also {
            if (filter(it)) {
                return it
            }
        }
    }

    val channel = Channel<T>()
    val observer = Observer<T> {
        val newValue = it ?: return@Observer
        if (filter(newValue)) {
            channel.send(Dispatchers.Main, newValue)
        }
    }

    return withContext(Dispatchers.Main) {
        observeForever(observer)
        try {
            return@withContext channel.receive()
        } finally {
            removeObserver(observer)
        }
    }
}

/**
 * set data to live data.
 *
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
fun <T> MutableLiveData<T>.setValueAsync(
    context: CoroutineContext,
    factory: suspend (target: MutableLiveData<T>) -> T?
) {
    val self = this
    GlobalScope.launch(context) {
        val value = factory(self)
        withContext(Dispatchers.Main) {
            self.value = value
        }
    }
}

/**
 * set data to live data.
 *
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
fun <T> MutableLiveData<T>.setValueAsync(
    scope: CoroutineScope,
    context: CoroutineContext,
    factory: suspend (target: MutableLiveData<T>) -> T?
) {
    val self = this
    scope.launch(context) {
        val value = factory(self)
        withContext(Dispatchers.Main) {
            self.value = value
        }
    }
}