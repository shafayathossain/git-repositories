package com.example.github.repositories.base

import androidx.lifecycle.ViewModel
import com.example.github.repositories.MAX_ITEM_COUNT_IN_REPOSITORY_LIST
import com.example.github.repositories.data.RepositoryDTO

open class BaseViewModel: ViewModel() {

    val message = SingleLiveEvent<String>()
    val showLoader = SingleLiveEvent<Boolean>()

    fun populateRepositoryList(repositories: List<RepositoryDTO>?): List<RepositoryDTO> {
        return repositories
            ?.take(MAX_ITEM_COUNT_IN_REPOSITORY_LIST)
            ?.sortedBy { it.stargazers_count } ?: listOf()
    }
}