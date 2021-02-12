@file:Suppress("unused")

package com.eaglesakura.armyknife.android.extensions

import android.app.Application
import android.content.Context
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.eaglesakura.armyknife.android.internal.instanceOf
import kotlin.reflect.KClass

/**
 * Find fragment by FragmentManager with finder.
 *
 * e.g.)
 * val fragment = fragmentManager.find { it.tag == "main" }
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
@Suppress("UNCHECKED_CAST")
fun <T : Fragment> FragmentManager.find(finder: (frag: Fragment) -> Boolean): T? {
    fragments.forEach { fragment ->
        if (finder(fragment)) {
            return fragment as T
        }
    }
    return null
}

/**
 * Finding the "T" Interface from Activity or parent fragments.
 *
 * e.g.)
 * val callback = fragment.findInterface(Callback::class)
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
@Suppress("UNCHECKED_CAST")
fun <T : Any> Fragment.findInterface(clazz: KClass<T>): T? {
    // find from parent
    var target: Fragment? = this
    while (target != null) {
        if (target.instanceOf(clazz.java)) {
            return target as T
        }
        target = target.parentFragment
    }

    if (activity?.instanceOf(clazz.java) == true) {
        return activity as T
    }

    return null
}

/**
 * Finding the "T" Interface from Activity or parent fragments.
 *
 * e.g.)
 * val callback: Callback?
 *      get() = fragment.findInterface()
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
inline fun <reified T> Fragment.findInterface(): T? {
    // find from parent
    var target: Fragment? = this
    while (target != null) {
        if (target is T) {
            return target
        }
        target = target.parentFragment
    }

    if (activity is T) {
        return activity as T
    }

    return null
}

/**
 * Finding the "T" Interface from Activity or parent fragments.
 *
 * e.g.)
 * val callback: Callback
 *      get() = fragment.requireInterface()
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
inline fun <reified T> Fragment.requireInterface(): T {
    return requireNotNull(findInterface()) {
        "Interface(${T::class.java.name}) not found."
    }
}

/**
 * Get ViewModel from Fragment with SavedStateViewModelFactory.
 *
 * e.g.)
 *
 * class ExampleFragment: Fragment() {
 *      val viewModel: ExampleViewModel by savedStateViewModels()
 * }
 *
 * @see SavedStateViewModelFactory
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
@Deprecated("remove this extension.", ReplaceWith(""))
@MainThread
inline fun <reified VM : ViewModel> Fragment.savedStateViewModels() = createViewModelLazy(
    VM::class,
    { viewModelStore },
    { SavedStateViewModelFactory(requireContext().applicationContext as Application, this) }
)

/**
 * Get ViewModel from FragmentActivity with SavedStateViewModelFactory.
 *
 * e.g.)
 *
 * class ExampleFragment: Fragment() {
 *      val viewModel: ExampleViewModel by activitySavedStateViewModels()
 * }
 *
 * @see androidx.fragment.app.FragmentActivity
 * @see SavedStateViewModelFactory
 */
@Deprecated("remove this extension.", ReplaceWith(""))
@MainThread
inline fun <reified VM : ViewModel> Fragment.activitySavedStateViewModels() = createViewModelLazy(
    VM::class,
    { requireActivity().viewModelStore },
    {
        SavedStateViewModelFactory(
            requireContext().applicationContext as Application,
            requireActivity()
        )
    }
)

/**
 * Get SavedStateHandle for Fragment.
 *
 * e.g.)
 *
 * class ExampleFragment: Fragment() {
 *
 *      var url: String
 *          get() = savedStateHandle.get("url")
 *          set(value) = savedStateHandle.set("url", value)
 * }
 *
 * @see SavedStateViewModelFactory
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
@Deprecated("remove this extension.", ReplaceWith(""))
val Fragment.savedStateHandle: SavedStateHandle
    get() = ViewModelProviders.of(
        this,
        SavedStateViewModelFactory(requireContext().applicationContext as Application, this)
    ).get(SavedStateHandleViewModel::class.java).savedStateHandle

/**
 * Context set to external live data.
 * when Fragment was destroyed, then set null.
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
fun Fragment.contextInto(liveData: MutableLiveData<Context>) {
    liveData.value = this.activity
    this.lifecycle.subscribe { event ->
        if (event == Lifecycle.Event.ON_DESTROY) {
            liveData.value = null
        } else {
            liveData.setValueIfChanged(this.activity)
        }
    }
}
