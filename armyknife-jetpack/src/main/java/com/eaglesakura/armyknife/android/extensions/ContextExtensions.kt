@file:Suppress("unused")

package com.eaglesakura.armyknife.android.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.os.Build
import android.provider.Settings
import android.view.LayoutInflater
import android.view.Surface
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

@Deprecated(
    "replace to ApplicationRuntime",
    ReplaceWith("ApplicationRuntime.getDeviceRotateDegree(context)")
)
val Context.deviceRotateDegree: Int
    get() {
        val surfaceRotation =
            (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.rotation
        when (surfaceRotation) {
            Surface.ROTATION_0 -> return 0
            Surface.ROTATION_90 -> return 90
            Surface.ROTATION_180 -> return 180
            Surface.ROTATION_270 -> return 270
        }
        return 0
    }

/**
 * If this app debugging now,
 * This property returns true,
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
val Context.debugMode: Boolean
    get() = packageManager.getApplicationInfo(packageName, 0)?.let { appInfo ->
        return appInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE == ApplicationInfo.FLAG_DEBUGGABLE
    } ?: false

/**
 * When developer mode enabled on this device, This property return true.
 * But, API Level less than 17, This property always returns false.
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
@Deprecated("typo, developerModeDevice", ReplaceWith("developerModeDevice"))
val Context.devloperModeDevice: Boolean
    @SuppressLint("ObsoleteSdkInt")
    get() = if (Build.VERSION.SDK_INT < 17) {
        false
    } else {
        Settings.Secure.getInt(contentResolver, Settings.Global.ADB_ENABLED, 0) != 0
    }

/**
 * When developer mode enabled on this device, This property return true.
 * But, API Level less than 17, This property always returns false.
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
val Context.developerModeDevice: Boolean
    get() = Settings.Secure.getInt(contentResolver, Settings.Global.ADB_ENABLED, 0) != 0

/**
 * Shortcut to 'LayoutInflater.from(this)'
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
val Context.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(this)

/**
 * This method returns true when android-device connected to network.
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
@SuppressLint("MissingPermission")
fun Context.isConnectedNetwork(): Boolean {
    val service = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return service.activeNetworkInfo?.isConnected ?: false
}

/**
 * Wrap to ContextCompat.getDrawable() method.
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
fun Context.getDrawableCompat(@DrawableRes resId: Int): Drawable? {
    return ContextCompat.getDrawable(this, resId)
}

/**
 * Wrap to ContextCompat.getColor() method.
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
fun Context.getColorCompat(@ColorRes resId: Int): Int {
    return ContextCompat.getColor(this, resId)
}
