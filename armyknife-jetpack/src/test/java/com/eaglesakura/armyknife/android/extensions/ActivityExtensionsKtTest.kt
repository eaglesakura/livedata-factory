package com.eaglesakura.armyknife.android.extensions

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eaglesakura.armyknife.android.junit4.extensions.compatibleBlockingTest
import com.eaglesakura.armyknife.android.junit4.extensions.makeActivity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
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
}