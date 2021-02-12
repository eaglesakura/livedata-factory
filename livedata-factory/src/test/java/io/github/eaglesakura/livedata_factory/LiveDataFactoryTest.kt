package io.github.eaglesakura.livedata_factory

import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eaglesakura.armyknife.android.junit4.extensions.compatibleBlockingTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.yield
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LiveDataFactoryTest {
    @Test
    fun filter() = compatibleBlockingTest(Dispatchers.Main) {
        val origin = MutableLiveData<String>()
        val secureUrl = LiveDataFactory.filter(origin) { it?.startsWith("https://") ?: false }
        secureUrl.observeForever { }
        Assert.assertNotEquals(origin, secureUrl)

        origin.value = "http://example.com"
        yield()
        Assert.assertNull(secureUrl.value)

        origin.value = "https://example.com"
        yield()
        Assert.assertEquals("https://example.com", secureUrl.value)
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

        Assert.assertEquals("ABnull", transformed.value)

        text2.value = "C"

        yield()
        Assert.assertEquals("ABC", transformed.value)
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

        Assert.assertEquals(null, transformed.value)

        text2.value = "C"

        yield()
        Assert.assertEquals("ABC", transformed.value)
    }
}
