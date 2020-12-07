package com.eaglesakura.armyknife.android.extensions

import android.os.Bundle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eaglesakura.armyknife.runtime.extensions.encodeBase64
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BundleExtensionsKtTest {

    @Test
    fun putMarshalParcelable() {
        val bundle = Bundle()
        bundle.putMarshalParcelable("name", ExampleDataClass("example@example.com"))

        assertEquals(ExampleDataClass::class.java.name, bundle.getString("name@class"))
        assertEquals(
            ExampleDataClass("example@example.com").marshal().encodeBase64(),
            bundle.getByteArray("name@marshal")!!.encodeBase64()
        )

        bundle.deepCopy<Bundle>().also { copied ->
            val extra = copied.getMarshalParcelable<ExampleDataClass>("name")
            assertNotNull(extra)
            assertEquals("example@example.com", extra!!.string)
        }
    }

    @Test
    fun putMarshalParcelable_null() {
        val bundle = Bundle()
        bundle.putMarshalParcelable("name", null)

        assertNull(bundle.getString("name@class"))
        assertNull(bundle.getByteArray("name@marshal"))

        bundle.deepCopy<Bundle>().also { copied ->
            val extra = copied.getMarshalParcelable<ExampleDataClass>("name")
            assertNull(extra)
        }
    }
}
