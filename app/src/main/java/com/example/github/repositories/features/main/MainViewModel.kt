package com.example.github.repositories.features.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.github.repositories.base.BaseViewModel
import com.example.github.repositories.data.model.RepositoryDTO
import com.example.github.repositories.data.model.Response
import com.example.github.repositories.data.network.exception.Failure
import com.example.github.repositories.data.network.network_utils.executeRetrofitCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {
    val repositories = MutableLiveData<List<RepositoryDTO>>()

    fun fetchItems() {
        viewModelScope.launch(Dispatchers.IO) {
            showLoader.postValue(true)
            delay(1_000) // This is to simulate network latency, please don't remove!
            executeRetrofitCall(
                ioDispatcher = Dispatchers.IO,
                retrofitCall = {
                    dataSource.fetchItems()
                }
            ).let { response ->
                showLoader.postValue(false)
                response.mapSuccess { responseItems -> responseItems }
            }.either(::handleError, ::setRepositories)
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

    private fun setRepositories(response: Response) {
        val itemsFoUi = getRepositoriesForUi(response.items)
        repositories.postValue(itemsFoUi)
    }

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel() as T
        }
    }
}