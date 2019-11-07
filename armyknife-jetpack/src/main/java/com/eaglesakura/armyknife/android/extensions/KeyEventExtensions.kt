@file:Suppress("unused")

package com.eaglesakura.armyknife.android.extensions

import android.view.KeyEvent

/**
 * this is event of Back Key?
 *
 * @author @eaglesakura
 * @link https://github.com/eaglesakura/armyknife-jetpack
 */
fun KeyEvent.isBackKeyRelease(): Boolean {
    return action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK
}