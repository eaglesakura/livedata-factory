package com.eaglesakura.armyknife.android.extensions

import android.content.Intent
import android.os.Parcelable
import kotlin.reflect.KClass

/**
 * putExtra with Marshal value to generic data.
 * This Intent receiver need not to know Parcelable class.
 * You will avoidance  'BadParcelableException'.
 *
 * * Class -> String(class.name)
 * * Parcelable object -> Parcelable.marshal()
 *
 * @see Parcelable.marshal
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
fun Intent.putMarshalParcelableExtra(name: String, value: Parcelable?): Intent {
    putExtra("$name@class", value?.javaClass?.name)
    putExtra("$name@marshal", value?.marshal())
    return this
}

/**
 * getExtra with Marshal value from generic data.
 *
 * * Class -> String(class.name)
 * * Parcelable object -> Parcelable.marshal()
 *
 * @see Parcelable.marshal
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
fun <T : Parcelable> Intent.getMarshalParcelableExtra(name: String): T? {
    val clazz = getStringExtra("$name@class") ?: return null
    val marshal = getByteArrayExtra("$name@marshal") ?: return null

    @Suppress("UNCHECKED_CAST")
    return (Class.forName(clazz).kotlin as KClass<Parcelable>).unmarshal(marshal) as T
}