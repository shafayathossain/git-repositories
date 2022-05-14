package com.example.github.repositories.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.github.repositories.base.SingleLiveEvent
import com.example.github.repositories.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {

    private val retrofit = Retrofit.Builder()
        .baseUrl(GITHUB_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service: GitHubEndpoints = retrofit.create(GitHubEndpoints::class.java)

    val repositories = MutableLiveData<List<RepositoryDTO>>()
    val message = SingleLiveEvent<String>()

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel() as T
        }
    }

    fun fetchItems() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1_000) // This is to simulate network latency, please don't remove!
            executeRetrofitCall(
                ioDispatcher = Dispatchers.IO,
                retrofitCall = {
                    service.searchRepositories(QUERY, SORT, ORDER)
                }
            ).let { response -> response.mapSuccess { responseItems -> responseItems } }
                .either(::handleError, ::handleSuccess)
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

    private fun handleSuccess(response: Response) {
        repositories.postValue(response?.items)
    }
}