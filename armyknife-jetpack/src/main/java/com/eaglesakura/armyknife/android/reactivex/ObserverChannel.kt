package com.eaglesakura.armyknife.android.reactivex

import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

internal class ObserverChannel<T>(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val channel: Channel<T> = Channel(Channel.UNLIMITED)
) : Channel<T> by channel, Observer<T> {
    private var disposable: Disposable? = null

    private val lock = ReentrantLock()

    private fun dispose(): Unit = lock.withLock {
        disposable?.dispose()
        disposable = null
    }

    override fun cancel() {
        dispose()
        channel.cancel()
    }

    override fun cancel(cause: CancellationException?) {
        dispose()
        channel.cancel(cause)
    }

    override fun close(cause: Throwable?): Boolean {
        dispose()
        return channel.close(cause)
    }

    override fun onSubscribe(d: Disposable) {
        disposable = d
    }

    override fun onError(e: Throwable) {
        cancel(e as? CancellationException)
    }

    override fun onComplete() {
        close()
    }

    override fun onNext(value: T) {
        GlobalScope.launch(dispatcher) {
            send(value)
        }
    }
}
