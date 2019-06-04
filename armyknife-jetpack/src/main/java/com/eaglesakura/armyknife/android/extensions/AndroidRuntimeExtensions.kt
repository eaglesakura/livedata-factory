package com.eaglesakura.armyknife.android.extensions

import androidx.core.os.CancellationSignal
import com.eaglesakura.armyknife.runtime.extensions.CancelCallback

/**
 * CancellationSignal to CancelSignal.
 * This function supports only CancellationSignal in "androidx".
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/army-knife
 */
@Deprecated("delete this.")
fun CancellationSignal.asCancelCallback(): CancelCallback {
    return fun(): Boolean {
        return isCanceled
    }
}