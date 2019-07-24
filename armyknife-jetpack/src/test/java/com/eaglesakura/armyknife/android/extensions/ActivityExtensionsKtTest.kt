package com.eaglesakura.armyknife.android.extensions

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eaglesakura.armyknife.android.junit4.extensions.compatibleBlockingTest
import com.eaglesakura.armyknife.android.junit4.extensions.inInstrumentationTest
import com.eaglesakura.armyknife.android.junit4.extensions.makeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ActivityExtensionsKtTest {

    @Test
    fun savedStateHandle() = compatibleBlockingTest {
        val activity = makeActivity()
        assertEquals(activity.savedStateHandle, activity.savedStateHandle) // single state.

        assertNull(activity.savedStateHandle.get<String>("url"))
        activity.savedStateHandle.set("url", "https://example.com")
        assertEquals("https://example.com", activity.savedStateHandle.get<String>("url"))
    }

    @Test
    fun contextInto() = compatibleBlockingTest {
        val activity = makeActivity()
        val context = withContext(Dispatchers.Main) {
            MutableLiveData<Context>().also { activity.contextInto(it) }
        }

        assertEquals(activity, context.value)
        activity.finish()
        kotlinx.coroutines.delay(1000)
        assertTrue(activity.isFinishing)

        inInstrumentationTest {
            assertEquals(Lifecycle.State.DESTROYED, activity.lifecycle.currentState)
            assertNull(context.value)
        }
    }
}