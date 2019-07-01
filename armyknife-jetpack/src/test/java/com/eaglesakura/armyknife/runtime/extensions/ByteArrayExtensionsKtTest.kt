package com.eaglesakura.armyknife.runtime.extensions

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eaglesakura.armyknife.runtime.Random
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.nio.charset.Charset

@RunWith(AndroidJUnit4::class)
class ByteArrayExtensionsKtTest {
    @Test
    fun encodeBase64() {
        for (i in 0 until 1000) {
            val value = Random.largeString()
            assertEquals(
                value,
                value.toByteArray(Charset.defaultCharset()).encodeBase64().decodeBase64().toString(
                    Charset.defaultCharset()
                )
            )
        }
    }

    @Test
    fun encodeMD5() {
        assertEquals("827ccb0eea8a706c4c34a16891f84e7b", "12345".toByteArray().encodeMD5())
    }

    @Test
    fun encodeSHA1() {
        assertEquals("8cb2237d0679ca88db6464eac60da96345513964", "12345".toByteArray().encodeSHA1())
    }

    @Test
    fun encodeSHA256() {
        assertEquals(
            "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5",
            "12345".toByteArray().encodeSHA256()
        )
    }

    @Test
    fun encodeSHA512() {
        assertEquals(
            "3627909a29c31381a071ec27f7c9ca97726182aed29a7ddd2e54353322cfb30abb9e3a6df2ac2c20fe23436311d678564d0c8d305930575f60e2d3d048184d79",
            "12345".toByteArray().encodeSHA512()
        )
    }
}