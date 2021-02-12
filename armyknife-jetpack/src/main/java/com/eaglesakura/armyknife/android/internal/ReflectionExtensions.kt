package com.eaglesakura.armyknife.android.internal

/**
 * check any instance implemented `Class<>`.
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-runtime
 */
internal fun Any.instanceOf(clazz: Class<*>): Boolean {
    return try {
        javaClass.asSubclass(clazz) != null
    } catch (e: Exception) {
        false
    }
}
