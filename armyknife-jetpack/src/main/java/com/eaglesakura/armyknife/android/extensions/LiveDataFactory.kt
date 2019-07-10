package com.eaglesakura.armyknife.android.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * LiveData factory for some use case.
 */
@Suppress("unused")
object LiveDataFactory {

    /**
     * Live data write filter.
     * when `source` data on updated, then check filter function.
     * If it returns `true`, then write dst live data.
     *
     * e.g.)
     *
     * val origin = MutableLiveData<String>()
     * val secureUrl = LiveDataFactory.filter(origin) { it.startsWith("https://") }
     * assertNotEquals(origin, secureUrl)
     *
     * origin.value = "http://example.com"
     * assertNull(secureUrl.value)
     *
     * origin.value = "https://example.com"
     * assertEquals("https://example.com", secureUrl.value)
     *
     * @param source original LiveData
     * @param filter if write dst value, then return true.
     */
    fun <T> filter(source: LiveData<T>, filter: (value: T?) -> Boolean): LiveData<T> {
        return MediatorLiveData<T>().also { result ->
            result.addSource(source) {
                if (filter(it)) {
                    result.value = it
                }
            }
        }
    }

    /**
     * 1 live data to 1 data.
     */
    fun <A, T> transform(
        a: LiveData<A>,
        mapper: (a: A) -> T?
    ): LiveData<T> {
        return MediatorLiveData<T>().also { result ->
            result.addSource(a) {
                result.value = mapper(a.value ?: return@addSource)
            }
        }
    }

    /**
     * 2 live data to 1 data.
     */
    fun <A, B, T> transform(
        a: LiveData<A>,
        b: LiveData<B>,
        mapper: (a: A?, b: B?) -> T?
    ): LiveData<T> {
        return MediatorLiveData<T>().also { result ->
            result.addSource(a) {
                result.value = mapper(a.value, b.value)
            }
            result.addSource(b) {
                result.value = mapper(a.value, b.value)
            }
        }
    }

    /**
     * 3 live data to 1 data.
     */
    fun <A, B, C, T> transform(
        a: LiveData<A>,
        b: LiveData<B>,
        c: LiveData<C>,
        mapper: (a: A?, b: B?, c: C?) -> T?
    ): LiveData<T> {
        return MediatorLiveData<T>().also { result ->
            result.addSource(a) {
                result.value = mapper(
                    a.value,
                    b.value,
                    c.value
                )
            }
            result.addSource(b) {
                result.value = mapper(
                    a.value,
                    b.value,
                    c.value
                )
            }
            result.addSource(c) {
                result.value = mapper(
                    a.value,
                    b.value,
                    c.value
                )
            }
        }
    }

    /**
     * 4 live data to 1 data.
     */
    fun <A, B, C, D, T> transform(
        a: LiveData<A>,
        b: LiveData<B>,
        c: LiveData<C>,
        d: LiveData<D>,
        mapper: (a: A?, b: B?, c: C?, d: D?) -> T?
    ): LiveData<T> {
        return MediatorLiveData<T>().also { result ->
            result.addSource(a) {
                result.value = mapper(
                    a.value,
                    b.value,
                    c.value,
                    d.value
                )
            }
            result.addSource(b) {
                result.value = mapper(
                    a.value,
                    b.value,
                    c.value,
                    d.value
                )
            }
            result.addSource(c) {
                result.value = mapper(
                    a.value,
                    b.value,
                    c.value,
                    d.value
                )
            }
            result.addSource(d) {
                result.value = mapper(
                    a.value,
                    b.value,
                    c.value,
                    d.value
                )
            }
        }
    }

    /**
     * 5 live data to 1 data.
     */
    fun <A, B, C, D, E, T> transform(
        a: LiveData<A>,
        b: LiveData<B>,
        c: LiveData<C>,
        d: LiveData<D>,
        e: LiveData<E>,
        mapper: (a: A?, b: B?, c: C?, d: D?, e: E?) -> T?
    ): LiveData<T> {
        return MediatorLiveData<T>().also { result ->
            result.addSource(a) {
                result.value = mapper(
                    a.value,
                    b.value,
                    c.value,
                    d.value,
                    e.value
                )
            }
            result.addSource(b) {
                result.value = mapper(
                    a.value,
                    b.value,
                    c.value,
                    d.value,
                    e.value
                )
            }
            result.addSource(c) {
                result.value = mapper(
                    a.value,
                    b.value,
                    c.value,
                    d.value,
                    e.value
                )
            }
            result.addSource(d) {
                result.value = mapper(
                    a.value,
                    b.value,
                    c.value,
                    d.value,
                    e.value
                )
            }
            result.addSource(e) {
                result.value = mapper(
                    a.value,
                    b.value,
                    c.value,
                    d.value,
                    e.value
                )
            }
        }
    }

    /**
     * 6 live data to 1 data.
     */
    fun <A, B, C, D, E, F, T> transform(
        a: LiveData<A>,
        b: LiveData<B>,
        c: LiveData<C>,
        d: LiveData<D>,
        e: LiveData<E>,
        f: LiveData<F>,
        mapper: (a: A?, b: B?, c: C?, d: D?, e: E?, f: F?) -> T?
    ): LiveData<T> {
        return MediatorLiveData<T>().also { result ->
            result.addSource(a) {
                result.value = mapper(
                    a.value,
                    b.value,
                    c.value,
                    d.value,
                    e.value,
                    f.value
                )
            }
            result.addSource(b) {
                result.value = mapper(
                    a.value,
                    b.value,
                    c.value,
                    d.value,
                    e.value,
                    f.value
                )
            }
            result.addSource(c) {
                result.value = mapper(
                    a.value,
                    b.value,
                    c.value,
                    d.value,
                    e.value,
                    f.value
                )
            }
            result.addSource(d) {
                result.value = mapper(
                    a.value,
                    b.value,
                    c.value,
                    d.value,
                    e.value,
                    f.value
                )
            }
            result.addSource(e) {
                result.value = mapper(
                    a.value,
                    b.value,
                    c.value,
                    d.value,
                    e.value,
                    f.value
                )
            }
            result.addSource(f) {
                result.value = mapper(
                    a.value,
                    b.value,
                    c.value,
                    d.value,
                    e.value,
                    f.value
                )
            }
        }
    }
}
