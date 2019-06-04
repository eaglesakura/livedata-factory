package com.eaglesakura.armyknife.runtime.extensions

import androidx.core.os.CancellationSignal

/**
 * CancellationSignal to CancelSignal.
 * This function supports only CancellationSignal in "androidx".
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/army-knife
 */
@Deprecated(
    "package name was rename, replace to 'com.eaglesakura.armyknife.android.extensions'",
    ReplaceWith("com.eaglesakura.armyknife.android.extensions.CancellationSignal.asCancelCallback()")
)
fun CancellationSignal.asCancelCallback(): CancelCallback {
    return fun(): Boolean {
        return isCanceled
    }
}