package com.eaglesakura.armyknife.android.extensions

import android.content.SharedPreferences
import org.json.JSONArray

/**
 * put string-array data to SharedPreferences.
 * array-data encode to JSON by JSONArray(Android embedded impl)
 *
 * e.g.)
 * val stringArray = listOf("Apple", "Banana")
 * val preferences: SharedPreferences = ...
 * preferences.edit {
 *      putStringArray("key", stringArray)
 * }
 *
 * @see SharedPreferences.Editor.putString
 * @see JSONArray
 */
fun SharedPreferences.Editor.putStringArray(key: String, values: List<String>?) {
    if (values == null) {
        putString(key, null)
    } else {
        val serialized = JSONArray(values).toString(0)
        putString(key, serialized)
    }
}

/**
 * get string-array(or null) from SharedPreferences.
 * array-data encode to JSON by JSONArray(Android embedded impl)
 *
 * e.g.)
 * val preferences: SharedPreferences = ...
 * val stringArray: List<String>? = preferences.getStringArray("key")
 *
 * @see SharedPreferences.getString
 * @see JSONArray
 */
fun SharedPreferences.getStringArray(key: String): List<String>? {
    return getString(key, null)?.let { json ->
        JSONArray(json).let { jsonArray ->
            val result = mutableListOf<String>()
            for (index in 0 until jsonArray.length()) {
                result.add(jsonArray.getString(index))
            }
            result
        }
    }
}