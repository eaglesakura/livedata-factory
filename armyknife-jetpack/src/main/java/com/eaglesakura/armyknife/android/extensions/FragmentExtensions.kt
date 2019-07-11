@file:Suppress("unused")

package com.eaglesakura.armyknife.android.extensions

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.eaglesakura.armyknife.runtime.extensions.instanceOf
import kotlin.reflect.KClass

/**
 * Find fragment by FragmentManager with finder.
 *
 * e.g.)
 * val fragment = fragmentManager.find { it.tag == "main" }
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/army-knife
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
 * @link https://github.com/eaglesakura/army-knife
 */
@Suppress("UNCHECKED_CAST")
fun <T : Any> Fragment.findInterface(clazz: KClass<T>): T? {
    // find from parent
    var target: Fragment? = this
    while (target != null) {
        if (target.instanceOf(clazz)) {
            return target as T
        }
        target = target.parentFragment
    }

    if (activity?.instanceOf(clazz) == true) {
        return activity as T
    }

    return null
}

/**
 * Finding the "T" Interface from Activity or parent fragments.
 *
 * e.g.)
 * val callback: Callback? = fragment.findInterface()
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/army-knife
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
 * Get ViewModel from Fragment with SavedStateViewModelFactory.
 *
 * e.g.)
 *
 * class ExampleFragment: Fragment() {
 *      val viewModel: ExampleViewModel by savedStateViewModels()
 * }
 *
 * @see SavedStateViewModelFactory
 */
@MainThread
inline fun <reified VM : ViewModel> Fragment.savedStateViewModels() = createViewModelLazy(
    VM::class,
    { viewModelStore },
    { SavedStateViewModelFactory(this) }
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
@MainThread
inline fun <reified VM : ViewModel> Fragment.activitySavedStateViewModels() = createViewModelLazy(
    VM::class,
    { requireActivity().viewModelStore },
    { SavedStateViewModelFactory(requireActivity()) }
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
val Fragment.savedStateHandle: SavedStateHandle
    get() = ViewModelProviders.of(this, SavedStateViewModelFactory(this))
        .get(SavedStateHandleViewModel::class.java).savedStateHandle
