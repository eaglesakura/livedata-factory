package com.eaglesakura.armyknife.android.extensions

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eaglesakura.armyknife.android.junit4.extensions.compatibleBlockingTest
import com.eaglesakura.armyknife.android.junit4.extensions.makeFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FragmentExtensionsKtTest {

    @Test
    fun contextInto() = compatibleBlockingTest {
        val fragment = makeFragment(Fragment::class)
        val context = withContext(Dispatchers.Main) {
            MutableLiveData<Context>().also {
                fragment.contextInto(it)
            }
        }

        withContext(Dispatchers.Main) {
            assertEquals(context.value, fragment.activity)
        }
        kotlinx.coroutines.delay(1000)
        val activity = fragment.requireActivity()
        assertEquals(context.value, activity)

        withContext(Dispatchers.Main) {
            activity.supportFragmentManager.commit {
                remove(fragment)
            }
        }

        kotlinx.coroutines.delay(1000)
        assertNull(fragment.activity)
        assertNull(context.value)
    }
}
