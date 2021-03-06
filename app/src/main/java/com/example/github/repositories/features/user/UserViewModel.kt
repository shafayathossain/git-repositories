package com.example.github.repositories.features.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.github.repositories.base.BaseViewModel
import com.example.github.repositories.data.model.RepositoryDTO
import com.example.github.repositories.data.model.UserDTO
import com.example.github.repositories.data.network.exception.Failure
import com.example.github.repositories.data.network.network_utils.executeRetrofitCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UserViewModel : BaseViewModel() {
    val user = MutableLiveData<UserDTO>()
    val repositories = MutableLiveData<List<RepositoryDTO>>()

    fun fetchUser(username: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            username?.let {
                showLoader.postValue(true)
                delay(1_000) // This is to simulate network latency, please don't remove!
                executeRetrofitCall(
                    ioDispatcher = Dispatchers.IO,
                    retrofitCall = {
                        dataSource.fetchUser(username)
                    }
                ).let { response ->
                    response.mapSuccess { responseItems -> responseItems }
                }.either(::handleError, ::setUserDto)
            }
        }
    }

    fun fetchRepositories(reposUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1_000) // This is to simulate network latency, please don't remove!
            executeRetrofitCall(
                ioDispatcher = Dispatchers.IO,
                retrofitCall = {
                    dataSource.fetchUserRepositories(reposUrl)
                }
            ).let { response ->
                showLoader.postValue(false)
                response.mapSuccess { responseItems -> responseItems }
            }.either(::handleError, ::setRepositories)
        }
    }

    private fun handleError(failure: Failure) {
        showLoader.postValue(false)
        message.postValue(failure.message)
    }

    private fun setUserDto(userDto: UserDTO) {
        user.postValue(userDto)
    }

    private fun setRepositories(items: List<RepositoryDTO>) {
        val itemsFoUi = getRepositoriesForUi(items)
        repositories.postValue(itemsFoUi)
    }

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UserViewModel() as T
        }
    }
}