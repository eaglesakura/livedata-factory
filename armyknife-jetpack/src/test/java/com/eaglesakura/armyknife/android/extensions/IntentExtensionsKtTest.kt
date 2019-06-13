package com.eaglesakura.armyknife.android.extensions

import android.content.Intent
import android.os.Parcelable
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eaglesakura.armyknife.runtime.extensions.encodeBase64
import kotlinx.android.parcel.Parcelize
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IntentExtensionsKtTest {

    @Test
    fun putMarshalParcelableExtra() {
        val intent = Intent()
        intent.putMarshalParcelableExtra("name", ExampleDataClass("example@example.com"))

        assertEquals(ExampleDataClass::class.java.name, intent.getStringExtra("name@class"))
        assertEquals(ExampleDataClass("example@example.com").marshal().encodeBase64(), intent.getByteArrayExtra("name@marshal").encodeBase64())

        intent.deepCopy<Intent>().also { copied ->
            val extra = copied.getMarshalParcelableExtra<ExampleDataClass>("name")
            assertNotNull(extra)
            assertEquals("example@example.com", extra!!.string)
        }
    }
}

@Parcelize
class ExampleDataClass(
    val string: String
) : Parcelable