package com.example.github.repositories.base

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import com.example.github.repositories.MAX_ITEM_COUNT_IN_REPOSITORY_LIST
import com.example.github.repositories.data.model.RepositoryDTO

open class BaseViewModel: ViewModel() {

    val message = SingleLiveEvent<String>()
    val showLoader = SingleLiveEvent<Boolean>()

    fun getRepositoriesForUi(items: List<RepositoryDTO>): List<RepositoryDTO> {
        val mItems = populateRepositoryList(items)
        for (i in mItems.indices) {
            val item = mItems[i]
            item.name_in_list = getItemNameForList(i, item)
        }
        return mItems
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getItemNameForList(position: Int, item: RepositoryDTO): String {
        return "#" + (position + 1) + ": " + item.full_name?.uppercase()
    }

    fun populateRepositoryList(repositories: List<RepositoryDTO>?): List<RepositoryDTO> {
        return repositories
            ?.sortedBy { it.stargazers_count }
            ?.reversed()
            ?.take(MAX_ITEM_COUNT_IN_REPOSITORY_LIST) ?: listOf()
    }
}