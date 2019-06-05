package com.eaglesakura.armyknife.runtime.extensions

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eaglesakura.armyknife.runtime.Random
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StringExtensionsKtTest {

    @Test
    fun base64byReflection() {
        val androidBase64 = Class.forName("android.util.Base64")
        androidBase64.getMethod("encodeToString", ByteArray::class.java, Int::class.java)
        androidBase64.getMethod("decode", String::class.java, Int::class.java)
    }

    @Test
    fun encodeAndDecode() {
        for (i in 0 until 9999) {
            val init = Random.largeString()
            assertEquals(init, String(init.toByteArray().encodeBase64().decodeBase64()))
        }
    }

    @Test
    fun base64() {
        assertEquals("MTIzNDU", "12345".toByteArray().encodeBase64())
    }
}