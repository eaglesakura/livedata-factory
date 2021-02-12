package com.eaglesakura.armyknife.android.extensions

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

/**
 *
 * @see androidx.fragment.app.FragmentActivity.savedStateHandle
 */
@Deprecated("DON'T USE THIS")
internal class SavedStateHandleViewModel internal constructor(
    val savedStateHandle: SavedStateHandle
) : ViewModel() {
    init {
        Log.e("armyknife", "DON'T USE!!, see) https://github.com/eaglesakura/armyknife-jetpack")
    }
}
