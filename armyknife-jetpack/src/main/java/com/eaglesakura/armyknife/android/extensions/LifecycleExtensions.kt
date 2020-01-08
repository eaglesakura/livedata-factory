package com.eaglesakura.armyknife.android.extensions

import androidx.annotation.Keep
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.eaglesakura.armyknife.runtime.extensions.withChildContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import kotlin.coroutines.CoroutineContext

/**
 * Launch with Lifecycle scope.
 *
 * e.g.)
 * lifecycle.launch {
 *      // do something in worker.
 * }
 */
@Deprecated("replace to 'LifecycleOwner.lifecycleScope.launch'")
fun Lifecycle.launch(context: CoroutineContext, block: suspend CoroutineScope.() -> Unit): Job {
    val lifecycle = this
    return GlobalScope.launch(context) {
        coroutineContext.with(lifecycle)
        block(this)
    }
}

/**
 * Launch with Lifecycle scope.
 *
 * e.g.)
 * lifecycle.async {
 *      // do something in worker.
 * }
 */
@Deprecated("replace to 'LifecycleOwner.lifecycleScope.launch'")
fun <T> Lifecycle.async(
    context: CoroutineContext,
    block: suspend CoroutineScope.() -> T
): Deferred<T> {
    val lifecycle = this
    return GlobalScope.async(context) {
        coroutineContext.with(lifecycle)
        block(this)
    }
}

/**
 * Subscribe lifecycle's event.
 *
 * e.g.)
 * fragment.lifecycle.subscribe { event ->
 *      if(event == Lifecycle.Event.ON_RESUME) {
 *          // do something.
 *      }
 * }
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
fun Lifecycle.subscribe(receiver: (event: Lifecycle.Event) -> Unit) {
    this.addObserver(object : LifecycleObserver {
        @Keep
        @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
        fun onAny(@Suppress("UNUSED_PARAMETER") source: LifecycleOwner, event: Lifecycle.Event) {
            receiver(event)
        }
    })
}

/**
 * Subscribe event with cancel callback.
 * If you should be ignore receiver, call "cancel()" function.
 *
 * e.g.)
 * fragment.lifecycle.subscribe { event, cancel ->
 *      if(event == Lifecycle.Event.ON_RESUME) {
 *          // do something.
 *
 *          cancel() // cancel subscribe events.
 *      }
 * }
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
fun Lifecycle.subscribeWithCancel(receiver: (event: Lifecycle.Event, cancel: () -> Unit) -> Unit) {
    val self = this
    self.addObserver(object : LifecycleObserver {
        @Keep
        @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
        fun onAny(@Suppress("UNUSED_PARAMETER") source: LifecycleOwner, event: Lifecycle.Event) {
            @Suppress("MoveLambdaOutsideParentheses")
            receiver(event, { self.removeObserver(this) })
        }
    })
}

/**
 * Suspend current coroutines context until receive lifecycle event.
 *
 * e.g.)
 * suspend fun awaitToResume() {
 *      delay(fragment.lifecycle, Lifecycle.Event.ON_RESUME)
 *
 *      // do something, fragment on resumed.
 * }
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
suspend fun delay(lifecycle: Lifecycle, targetEvent: Lifecycle.Event) {
    withChildContext(Dispatchers.Main) {
        yield()

        if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
            throw CancellationException("Lifecycle destroyed")
        }

        val channel = Channel<Lifecycle.Event>()
        lifecycle.subscribeWithCancel { event, cancel ->
            if (event == targetEvent) {
                // resume coroutines
                launch(Dispatchers.Main) {
                    channel.send(event)
                }
                cancel()
                return@subscribeWithCancel
            }

            if (event == Lifecycle.Event.ON_DESTROY) {
                // do not receive!!
                launch(Dispatchers.Main) {
                    channel.close(CancellationException("Lifecycle on destroy"))
                }
                cancel()
            }
        }
        channel.receive()
    }
}

/**
 * Suspend current coroutines context until receive lifecycle event.
 *
 * e.g.)
 * suspend fun awaitToResume() {
 *      delay(fragment.lifecycle, Lifecycle.State.RESUMED)
 *
 *      // do something, fragment on resumed.
 * }
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
suspend fun delay(lifecycle: Lifecycle, targetState: Lifecycle.State) {
    withChildContext(Dispatchers.Main) {
        yield()

        if (lifecycle.currentState == targetState) {
            return@withChildContext
        }

        if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
            throw CancellationException("Lifecycle destroyed")
        }

        val channel = Channel<Unit>()
        lifecycle.subscribeWithCancel { event, cancel ->
            if (lifecycle.currentState == targetState) {
                // resume coroutines
                launch(Dispatchers.Main) {
                    channel.send(Unit)
                }
                cancel()
                return@subscribeWithCancel
            }

            if (event == Lifecycle.Event.ON_DESTROY) {
                // do not receive!!
                launch(Dispatchers.Main) {
                    channel.close(CancellationException("Lifecycle on destroy"))
                }
                cancel()
            }
        }
        channel.receive()
    }
}
