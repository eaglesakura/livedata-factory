package com.eaglesakura.armyknife.android.extensions

import android.content.ContentResolver
import android.net.Uri
import java.io.InputStream
import java.io.OutputStream

/**
 * Test compatible `openInputStream`
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
fun ContentResolver.openInputStreamCompat(uri: Uri): InputStream? {
    return if (uri.toString().startsWith(SCHEME_FILE)) {
        uri.toFileCompat().inputStream()
    } else {
        openInputStream(uri)
    }
}

/**
 * Test compatible `openOutputStream`
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
fun ContentResolver.openOutputStreamCompat(uri: Uri): OutputStream? {
    return if (uri.toString().startsWith(SCHEME_FILE)) {
        uri.toFileCompat().outputStream()
    } else {
        openOutputStream(uri)
    }
}
