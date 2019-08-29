@file:Suppress("unused")

package com.eaglesakura.armyknife.android.extensions

import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.MainThread
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProviders

/**
 * Find interface by Activity and children.
 * If it has many interfaces then returns 1st hit object.
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
inline fun <reified T> FragmentActivity.findInterface(): T? {
    if (this is T) {
        return this
    }

    supportFragmentManager.fragments.forEach { fragment ->
        fragment.findInterface<T>()?.also {
            return it
        }
    }

    return null
}

/**
 * Force closing Input Method.
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/army-knife
 */
fun Activity.closeIME() {
    closeIME(currentFocus ?: return)
}

/**
 * Force closing Input Method.
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
fun Activity.closeIME(focus: View) {
    try {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            focus.windowToken,
            0
        )
    } catch (e: Exception) {
    }
}

/**
 * Get ViewModel from Fragment with SavedStateViewModelFactory.
 *
 * e.g.)
 *
 * class ExampleActivity: AppCompatActivity() {
 *      val viewModel: ExampleViewModel by savedStateViewModels()
 * }
 *
 * @see SavedStateViewModelFactory
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
@MainThread
inline fun <reified VM : ViewModel> FragmentActivity.savedStateViewModels() = ViewModelLazy(
    viewModelClass = VM::class,
    storeProducer = { viewModelStore },
    factoryProducer = { SavedStateViewModelFactory(applicationContext as Application, this) }
)

/**
 * Get SavedStateHandle for FragmentActivity.
 *
 * e.g.)
 *
 * class ExampleActivity: AppCompatActivity() {
 *
 *      var url: String
 *          get() = savedStateHandle.get("url")
 *          set(value) = savedStateHandle.set("url", value)
 * }
 *
 * @see SavedStateViewModelFactory
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
val FragmentActivity.savedStateHandle: SavedStateHandle
    get() = ViewModelProviders.of(
        this,
        SavedStateViewModelFactory(applicationContext as Application, this)
    )
        .get(SavedStateHandleViewModel::class.java).savedStateHandle

/**
 * Context set to external live data.
 * when Activity was destroyed, then set null.
 */
fun FragmentActivity.contextInto(liveData: MutableLiveData<Context>) {
    val activity = this
    val lifecycle = this.lifecycle
    if (lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
        liveData.value = activity
    }
    lifecycle.subscribe { event ->
        if (event == Lifecycle.Event.ON_DESTROY) {
            liveData.value = null
        } else {
            liveData.setValueIfChanged(activity)
        }
    }
}