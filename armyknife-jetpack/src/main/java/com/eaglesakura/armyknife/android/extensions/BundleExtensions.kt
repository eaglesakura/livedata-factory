package com.eaglesakura.armyknife.android.extensions

import android.os.Bundle
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
 */
fun Bundle.putMarshalParcelable(name: String, value: Parcelable?) {
    putString("$name@class", value?.javaClass?.name)
    putByteArray("$name@marshal", value?.marshal())
}

/**
 * getExtra with Marshal value from generic data.
 *
 * * Class -> String(class.name)
 * * Parcelable object -> Parcelable.marshal()
 * @see Parcelable.marshal
 */
fun <T : Parcelable> Bundle.getMarshalParcelable(name: String): T? {
    val clazz = getString("$name@class") ?: return null
    val marshal = getByteArray("$name@marshal") ?: return null

    @Suppress("UNCHECKED_CAST")
    return (Class.forName(clazz).kotlin as KClass<Parcelable>).unmarshal(marshal) as T
}
