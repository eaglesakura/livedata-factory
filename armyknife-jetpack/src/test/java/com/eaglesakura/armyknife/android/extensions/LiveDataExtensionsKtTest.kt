package com.eaglesakura.armyknife.android.extensions

import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eaglesakura.armyknife.android.junit4.extensions.compatibleBlockingTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.plus
import kotlinx.coroutines.withTimeout
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LiveDataExtensionsKtTest {

    @Test
    fun setValueAsync() = compatibleBlockingTest(Dispatchers.Main) {
        val liveData = MutableLiveData<String>()
        liveData.setValueAsync(Dispatchers.IO) {
            kotlinx.coroutines.delay(1000)
            "OK"
        }

        assertEquals("OK", liveData.await())
    }

    @Test(expected = TimeoutCancellationException::class)
    fun setValueAsync_cancel() = compatibleBlockingTest(Dispatchers.Main) {
        val liveData = MutableLiveData<String>()

        val scope = Job()
        liveData.setValueAsync((GlobalScope + scope), Dispatchers.IO) {
            kotlinx.coroutines.delay(1000)
            "OK"
        }
        scope.cancel()

        withTimeout(2000) {
            liveData.await()
        }
    }
}