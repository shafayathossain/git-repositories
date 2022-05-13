package com.example.github.repositories.data

import android.content.Context
import com.example.github.repositories.base.SharedPrefUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LocalDataStore private constructor() {

    companion object {
        val instance = LocalDataStore()
        private lateinit var preferences: SharedPrefUtils

        fun initiate(context: Context) {
            preferences = SharedPrefUtils(
                context.getSharedPreferences(
                    SharedPrefUtils.PREF_APP,
                    Context.MODE_PRIVATE
                )
            )
        }
    }

    fun bookmarkRepo(repositoryDTO: RepositoryDTO, bookmarked: Boolean) {
        val bookmarks = getBookmarks().toMutableSet()

        if (bookmarked)
            bookmarks.add(repositoryDTO.id)
        else
            bookmarks.remove(repositoryDTO.id)

        val dataString = Gson().toJson(bookmarks.toList())
        preferences.saveData(SharedPrefUtils.KEY_BOOKMARKS, dataString)

    }

    fun getBookmarks(): List<Int> {
        val itemType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(preferences.getStringData(SharedPrefUtils.KEY_BOOKMARKS), itemType) ?: listOf()
    }
}