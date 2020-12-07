@file:Suppress("unused")

package com.eaglesakura.armyknife.android.extensions

import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import java.util.concurrent.TimeUnit

/**
 * fetch remote configs from firebase.
 *
 * e.g.)
 * FirebaseRemoteConfig.getInstance().fetch(12, TimeUnit.HOURS)
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
fun FirebaseRemoteConfig.fetch(cacheTime: Long, cacheTimeUnit: TimeUnit): Task<Void> {
    val cacheTimeSec = cacheTimeUnit.toSeconds(cacheTime)
    return fetch(cacheTimeSec)
}
