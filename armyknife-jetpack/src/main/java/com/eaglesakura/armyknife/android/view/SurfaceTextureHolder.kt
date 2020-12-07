package com.eaglesakura.armyknife.android.view

import android.view.SurfaceHolder
import java.io.Closeable

/**
 * SurfaceTexture bind to SurfaceHolder.
 *
 * This interface use preview of Camera.
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
@Deprecated("Delete this, replace to 'CameraX' in Jetpack.")
interface SurfaceTextureHolder : SurfaceHolder, Closeable {
    fun onResizeTexture(width: Int, height: Int)
}
