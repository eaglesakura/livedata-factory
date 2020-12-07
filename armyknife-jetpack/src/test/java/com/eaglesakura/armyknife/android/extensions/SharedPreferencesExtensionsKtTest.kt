package com.eaglesakura.armyknife.android.extensions

import android.content.Context
import androidx.core.content.edit
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eaglesakura.armyknife.android.junit4.extensions.targetApplication
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SharedPreferencesExtensionsKtTest {

    @Test
    fun putStringArray() {
        val preferences = targetApplication.getSharedPreferences("test.pref", Context.MODE_PRIVATE)
        preferences.edit(commit = true) {
            putStringArray("strings", listOf("b", "a", "0", "Hello"))
        }

        preferences.getStringArray("strings").also { list ->
            require(list != null)
            require(list.size == 4)
            assertEquals("b", list[0])
            assertEquals("a", list[1])
            assertEquals("0", list[2])
            assertEquals("Hello", list[3])
        }
    }
}
