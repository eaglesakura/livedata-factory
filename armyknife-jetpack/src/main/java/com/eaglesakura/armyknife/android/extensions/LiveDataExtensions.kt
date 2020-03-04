@file:Suppress("unused")

package com.eaglesakura.armyknife.android.extensions

import android.annotation.SuppressLint
import androidx.annotation.AnyThread
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.annimon.stream.Optional
import com.eaglesakura.armyknife.runtime.extensions.send
import com.eaglesakura.armyknife.runtime.extensions.withChildContext
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

private class ObserverWrapper<T>(private val observer: Observer<T>) : Observer<T> {
    override fun onChanged(t: T?) {
        observer.onChanged(t)
    }
}

/**
 * LiveData force active.
 * when owner on destroy, then LiveData will be inactive.
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
fun <T> LiveData<T>.forceActiveAlive(owner: LifecycleOwner) {
    observeAlive(owner, ObserverWrapper(Observer { /* drop value. */ }))
}

/**
 * LiveData force active.
 *
 * CAUTION)
 *  This live data CAN'T state change to inactive.
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
fun <T> LiveData<T>.forceActiveForever() {
    observeForever(ObserverWrapper(Observer { /* drop value. */ }))
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
 * @author @eaglesakura
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
    return withChildContext(Dispatchers.Main) {
        try {
            observeForever(observer)
            channel.receive()
        } finally {
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
 * @author @eaglesakura
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

    return withChildContext(Dispatchers.Main) {
        observeForever(observer)
        try {
            return@withChildContext channel.receive()
        } finally {
            removeObserver(observer)
        }
    }
}

/**
 * set data to live data.
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
fun <T> MutableLiveData<T>.setValueAsync(
    context: CoroutineContext,
    factory: suspend (target: MutableLiveData<T>) -> T?
) {
    val self = this
    GlobalScope.launch(context) {
        withChildContext(Dispatchers.Main) {
            self.value = factory(self)
        }
    }
}

/**
 * set value and await in any thread.
 *
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
@AnyThread
fun <T> MutableLiveData<T>.blockingSetValue(value: T?) {
    if (onUiThread) {
        @SuppressLint("WrongThread")
        this.value = value
    } else {
        val self = this
        runBlocking(Dispatchers.Main) {
            self.value = value
        }
    }
}

/**
 * set data in coroutines.
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
suspend fun <T> MutableLiveData<T>.setValueAsync(value: T?) {
    val self = this
    withChildContext(Dispatchers.Main) {
        self.value = value
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
    scope.launch(Dispatchers.Main + context) {
        val value = factory(self)
        self.value = value
    }
}

/**
 * set newValue.
 *
 * e.g.)
 *
 * val url = MutableLiveData<String>()
 * url.setValueIfChanged("https://example.com") // notify observers to 'https://example.com'
 * url.setValueIfChanged("https://example.com") // not notify.
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
fun <T> MutableLiveData<T>.setValueIfChanged(newValue: T?) {
    if (this.value != newValue) {
        this.value = newValue
    }
}

/**
 * set newValue.
 *
 * e.g.)
 *
 * val url = MutableLiveData<String>()
 * url.setValueIfChanged("https://example.com") { oldValue == newValue } // notify observers to 'https://example.com'
 * url.setValueIfChanged("https://example.com") { oldValue == newValue } // not notify.
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
fun <T> MutableLiveData<T>.setValueIfChanged(
    newValue: T?,
    equals: (oldValue: T?, newValue: T?) -> Boolean
) {
    if (!equals(this.value, newValue)) {
        this.value = newValue
    }
}

/**
 * Copy liveData.
 *
 * e.g.)
 * val repository: LiveData<ExampleRepository>
 * val url: LiveData<String>
 *
 * fun observeRepository(example: ExampleRepository) {
 *      // copy to other live-data.
 *      example.urlLiveData.copyTo(lifecycleOwner, url)
 * }
 *
 * @return `dst` object.
 */
@Deprecated("This extension has memory-leak bug.")
fun <T> LiveData<T>.copyTo(
    lifecycleOwner: LifecycleOwner,
    dst: MutableLiveData<T>
): MutableLiveData<T> {
    dst.value = this.value
    this.observeAlive(lifecycleOwner, Observer {
        dst.value = it
    })
    return dst
}

/**
 * LiveData convert to PublishSubject with Optional.
 * RxJava is not support Nullable, however this util can support the Nullable value.
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
fun <T> LiveData<T>.toNullablePublishSubject(lifecycle: LifecycleOwner): PublishSubject<Optional<T>> {
    ProcessLifecycleOwner.get().lifecycleScope
    val subject = PublishSubject.create<Optional<T>>()
    lifecycle.lifecycle.subscribe {
        if (it == Lifecycle.Event.ON_DESTROY) {
            subject.onComplete()
        }
    }
    this.observe(lifecycle, Observer {
        subject.onNext(Optional.ofNullable(it))
    })

    return subject
}

/**
 * Set value until Lifecycle destroy.
 *
 * e.g.)
 * // this value set to null
 * // on lifecycleOwner.onDestroy event.
 * val liveData: MutableLiveData<String> = ...
 * liveData.setValueWhenCreated(lifecycleOwner, "OK")
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
fun <T> MutableLiveData<T>.setValueWhenCreated(lifecycleOwner: LifecycleOwner, value: T?) {
    val self = this
    self.value = value
    lifecycleOwner.lifecycle.subscribe { event ->
        if (event == Lifecycle.Event.ON_DESTROY && self.value == value) {
            self.value = null
        }
    }
}

/**
 * Set value until Lifecycle destroy.
 *
 * e.g.)
 * // this value set to null
 * // on lifecycleOwner.onPause event.
 * val liveData: MutableLiveData<String> = ...
 * liveData.setValueWhenResumed(lifecycleOwner, "OK")
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
fun <T> MutableLiveData<T>.setValueWhenResumed(lifecycleOwner: LifecycleOwner, value: T?) {
    val self = this
    self.value = value
    lifecycleOwner.lifecycle.subscribe { event ->
        if (event == Lifecycle.Event.ON_PAUSE && self.value == value) {
            self.value = null
        }
    }
}
