package com.eaglesakura.armyknife.android.extensions

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eaglesakura.armyknife.android.ApplicationRuntime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HandlerExtensionsKtTest {

    @Test
    fun runBlockingOnUiThread_uiThread() {
        if (ApplicationRuntime.ROBOLECTRIC) {
            return
        }

        assertEquals(
            true,
            runBlocking(Dispatchers.Main) {
                runBlockingOnUiThread {
                    onUiThread
                }
            }
        )
    }

    @Test
    fun runBlockingOnUiThread_workerThread() {
        assertEquals(
            true,
            runBlockingOnUiThread {
                onUiThread
            }
        )
    }
}
