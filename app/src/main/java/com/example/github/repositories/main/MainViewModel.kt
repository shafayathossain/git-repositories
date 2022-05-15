package com.example.github.repositories.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.github.repositories.base.BaseViewModel
import com.example.github.repositories.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : BaseViewModel() {

    private val retrofit = Retrofit.Builder()
        .baseUrl(GITHUB_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service: GitHubEndpoints = retrofit.create(GitHubEndpoints::class.java)
    val repositories = MutableLiveData<List<RepositoryDTO>>()

    fun fetchItems() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1_000) // This is to simulate network latency, please don't remove!
            executeRetrofitCall(
                ioDispatcher = Dispatchers.IO,
                retrofitCall = {
                    service.searchRepositories(QUERY, SORT, ORDER)
                }
            ).let { response -> response.mapSuccess { responseItems -> responseItems } }
                .either(::handleError, ::setRepositories)
        }
    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchItems()
        }
    }

    private fun handleError(failure: Failure) {
        message.postValue(failure.message)
    }

    fun setRepositories(response: Response) {
        val mResponse = getRepositoriesForUi(response)
        repositories.postValue(populateRepositoryList(mResponse.items))
    }

    fun getRepositoriesForUi(response: Response): Response {
        for (i in 0 until response.items.size) {
            val item = response.items[i]
            item.name_in_list = getItemNameForList(i, item)
        }
        return response
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getItemNameForList(position: Int, item: RepositoryDTO): String {
        return "#" + (position + 1) + ": " + item.full_name?.uppercase()
    }

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel() as T
        }
    }
}