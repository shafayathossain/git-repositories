package com.example.github.repositories.base

import android.content.SharedPreferences

class SharedPrefUtils constructor(private val preferences: SharedPreferences) {
    companion object {
        const val PREF_APP = "pref_app"
        const val KEY_BOOKMARKS = "bookmarks"
    }

    /**
     * Gets boolean data.
     * @param key     the key
     * @return the boolean data
     */
    fun getBooleanData(key: String?): Boolean {
        return preferences
            .getBoolean(key, false)
    }

    /**
     * Gets int data.
     * @param key     the key
     * @return the int data
     */
    fun getIntData(key: String?): Int {
        return preferences.getInt(key, 0)
    }

    /**
     * Gets string data.
     * @param key     the key
     * @return the string data
     */
    // Get Data
    fun getStringData(key: String?): String? {
        return preferences.getString(key, null)
    }

    /**
     * Save data.
     * @param key     the key
     * @param val     the val
     */
    // Save Data
    fun saveData(key: String?, `val`: String?) {
        preferences.edit()
            .putString(key, `val`).apply()
    }

    /**
     * Save data.
     * @param key     the key
     * @param val     the val
     */
    fun saveData(key: String?, `val`: Int) {
        preferences.edit().putInt(key, `val`)
            .apply()
    }

    /**
     * Save data.
     * @param key     the key
     * @param val     the val
     */
    fun saveData(key: String?, `val`: Boolean) {
        preferences
            .edit()
            .putBoolean(key, `val`)
            .apply()
    }

    fun getSharedPrefEditor(pref: String?): SharedPreferences.Editor {
        return preferences.edit()
    }

    fun saveData(editor: SharedPreferences.Editor) {
        editor.apply()
    }

}