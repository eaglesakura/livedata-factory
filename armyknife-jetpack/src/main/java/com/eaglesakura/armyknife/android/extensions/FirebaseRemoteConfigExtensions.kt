@file:Suppress("unused")

package com.eaglesakura.armyknife.android.extensions

import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import java.util.concurrent.TimeUnit

@Deprecated(
    "deprecated access to Firebase.config.developerMode",
    ReplaceWith("/* FIXME: DELETE THIS */")
)
fun FirebaseRemoteConfig.setDebug(set: Boolean) {
    FirebaseRemoteConfigSettings.Builder().setDeveloperModeEnabled(set).build().let {
        setConfigSettings(it)
    }
}

/**
 * Developer Mode option.
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
@Deprecated(
    "deprecated access to Firebase.config.developerMode",
    ReplaceWith("/* FIXME: DELETE THIS */")
)
var FirebaseRemoteConfig.developerMode
    get() = info.configSettings.isDeveloperModeEnabled
    set(value) {
        setConfigSettings(
            FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(value).build()
        )
    }

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
    val cacheTimeSec =
        if (info.configSettings.isDeveloperModeEnabled) {
            0
        } else {
            cacheTimeUnit.toSeconds(cacheTime)
        }
    return fetch(cacheTimeSec)
}
