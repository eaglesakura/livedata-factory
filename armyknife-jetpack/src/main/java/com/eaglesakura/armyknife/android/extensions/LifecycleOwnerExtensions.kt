package com.eaglesakura.armyknife.android.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 *  Add finalize function.
 *
 *  e.g.)
 *      val resourceData: Closable = ...
 *      lifecycleOwner.addFinalizer {
 *          resourceData.close()
 *      }
 */
fun LifecycleOwner.registerFinalizer(onDestroy: (owner: LifecycleOwner) -> Unit) {
    lifecycle.subscribe {
        if (it == Lifecycle.Event.ON_DESTROY) {
            onDestroy(this)
        }
    }
}

/**
 * Launch with Lifecycle scope.
 *
 * e.g.)
 * fragment.launch {
 *      // do something in worker.
 * }
 */
@Deprecated(
        "replace to 'LifecycleOwner.lifecycleScope.launch'",
        ReplaceWith("lifecycleScope.launch")
)
fun LifecycleOwner.launch(
    context: CoroutineContext,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return lifecycleScope.launch(context) {
        block()
    }
}

/**
 * Launch with Lifecycle scope.
 *
 * e.g.)
 * fragment.async {
 *      // do something in worker.
 * }
 */
@Deprecated("replace to 'LifecycleOwner.lifecycleScope.async'", ReplaceWith("lifecycleScope.async"))
fun <T> LifecycleOwner.async(
    context: CoroutineContext,
    block: suspend CoroutineScope.() -> T
): Deferred<T> {
    return lifecycleScope.async(context) {
        block()
    }
}

/**
 * Access to ProcessLifecycleOwner.get() instance.
 * must implementation 'androidx.lifecycle:lifecycle-process:2.1.0' module to your project.
 */
val processLifecycleOwner: LifecycleOwner
    get() = ProcessLifecycleOwner.get()
