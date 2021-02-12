package com.eaglesakura.armyknife.android.extensions

import android.net.Uri
import androidx.core.net.toFile
import java.io.File
import java.net.URLDecoder

internal const val SCHEME_FILE = "file://"

/**
 * Uri to file convert support on Windows path.
 */
fun Uri.toFileCompat(): File {
    val path = this.path ?: ""
    return if (path.isEmpty()) {
        // Windows path.
        val parsedPath = toString().substring(SCHEME_FILE.length)
        File(URLDecoder.decode(parsedPath, "UTF-8"))
    } else {
        toFile()
    }
}
