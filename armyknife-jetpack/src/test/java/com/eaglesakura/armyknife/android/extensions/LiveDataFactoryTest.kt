package com.eaglesakura.armyknife.android.extensions

import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eaglesakura.armyknife.android.junit4.extensions.compatibleBlockingTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.yield
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LiveDataFactoryTest {
    @Test
    fun filter() = compatibleBlockingTest(Dispatchers.Main) {
        val origin = MutableLiveData<String>()
        val secureUrl = LiveDataFactory.filter(origin) { it?.startsWith("https://") ?: false }
        secureUrl.observeForever { }
        assertNotEquals(origin, secureUrl)

        origin.value = "http://example.com"
        yield()
        assertNull(secureUrl.value)

        origin.value = "https://example.com"
        yield()
        assertEquals("https://example.com", secureUrl.value)
    }

    @Test
    fun transform3_nullable() = compatibleBlockingTest(Dispatchers.Main) {
        val text0 = MutableLiveData<String>()
        val text1 = MutableLiveData<String>()
        val text2 = MutableLiveData<String>()

        val transformed = LiveDataFactory.transformNullable(text0, text1, text2) { a, b, c ->
            "$a$b$c"
        }
        transformed.observeForever { }
        yield()
        text0.value = "A"
        text1.value = "B"
        yield()

        assertEquals("ABnull", transformed.value)

        text2.value = "C"

        yield()
        assertEquals("ABC", transformed.value)
    }

    @Test
    fun transform3_nonnull() = compatibleBlockingTest(Dispatchers.Main) {
        val text0 = MutableLiveData<String>()
        val text1 = MutableLiveData<String>()
        val text2 = MutableLiveData<String>()

        val transformed = LiveDataFactory.transform(text0, text1, text2) { a, b, c ->
            "$a$b$c"
        }
        transformed.observeForever { }
        yield()
        text0.value = "A"
        text1.value = "B"
        yield()

        assertEquals(null, transformed.value)

        text2.value = "C"

        yield()
        assertEquals("ABC", transformed.value)
    }
}
