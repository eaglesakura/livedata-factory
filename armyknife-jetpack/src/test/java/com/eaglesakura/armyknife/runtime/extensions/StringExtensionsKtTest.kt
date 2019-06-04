package com.eaglesakura.armyknife.runtime.extensions

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StringExtensionsKtTest {

    @Test
    fun base64byReflection() {
        val androidBase64 = Class.forName("android.util.Base64")
        androidBase64.getMethod("encodeToString", ByteArray::class.java, Int::class.java)
        androidBase64.getMethod("decode", ByteArray::class.java, Int::class.java)
    }

    @Test
    fun base64() {
        assertEquals("MTIzNDU", "12345".toByteArray().encodeBase64())
    }
}