package com.eaglesakura.armyknife.android.extensions

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

/**
 *
 * @see androidx.fragment.app.FragmentActivity.savedStateHandle
 */
internal class SavedStateHandleViewModel internal constructor(
    val savedStateHandle: SavedStateHandle
) : ViewModel()