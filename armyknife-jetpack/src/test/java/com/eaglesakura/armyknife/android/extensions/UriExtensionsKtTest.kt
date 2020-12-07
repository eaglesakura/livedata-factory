package com.eaglesakura.armyknife.android.extensions

import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eaglesakura.armyknife.android.junit4.extensions.compatibleTest
import com.eaglesakura.armyknife.android.junit4.extensions.targetApplication
import java.io.File
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UriExtensionsKtTest {

    @Test
    fun toFileCompat() = compatibleTest {
        val file = File(targetApplication.cacheDir, "temp")
        Log.d(TAG, "file: ${file.absolutePath}")
        val fromFile = Uri.fromFile(file)
        val parsed = Uri.parse(fromFile.toString())
        Log.d(TAG, "toFile: ${parsed.toFile().absolutePath}")
        Log.d(TAG, "toFileCompat: ${parsed.toFileCompat().absolutePath}")
        assertEquals(file.absolutePath, parsed.toFileCompat().absolutePath)
    }

    companion object {
        const val TAG = "UriExtensionsKtTest"
    }
}
