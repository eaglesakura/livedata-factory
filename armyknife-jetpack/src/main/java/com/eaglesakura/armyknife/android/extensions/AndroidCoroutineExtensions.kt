package com.eaglesakura.armyknife.android.extensions

import androidx.lifecycle.Lifecycle
import java.util.concurrent.CancellationException
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive

/**
 * Link to lifecycle.
 * When destroyed a lifecycle object then cancel coroutine.
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
@Deprecated("replace to LifecycleOwner.lifecycleScope.launchWhenXXX {}")
fun CoroutineContext.with(lifecycle: Lifecycle) {
    val context = this
    lifecycle.subscribeWithCancel { event, cancel ->
        if (!context.isActive) {
            cancel()
            return@subscribeWithCancel
        }
        if (event == Lifecycle.Event.ON_DESTROY) {
            try {
                context.cancel(CancellationException("Lifecycle is destroyed."))
            } catch (e: Throwable) {
                // drop error.
            }
            cancel()
        }
    }
}
