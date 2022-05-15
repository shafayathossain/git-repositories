package com.example.github.repositories.user

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

class UserViewModel : BaseViewModel() {

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UserViewModel() as T
        }
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(GITHUB_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service: GitHubEndpoints = retrofit.create(GitHubEndpoints::class.java)

    val user = MutableLiveData<UserDTO>()
    val repositories = MutableLiveData<List<RepositoryDTO>>()

    fun fetchUser(username: String?) {

        viewModelScope.launch(Dispatchers.IO) {
            username?.let {
                delay(1_000) // This is to simulate network latency, please don't remove!
                val response = service.getUser(username).execute()
                user.postValue(response.body()!!)
            }
        }
    }

    fun fetchRepositories(reposUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            showLoader.postValue(true)
            delay(1_000) // This is to simulate network latency, please don't remove!
            executeRetrofitCall(
                ioDispatcher = Dispatchers.IO,
                retrofitCall = {
                    service.getUserRepositories(reposUrl)
                }
            ).let { response ->
                showLoader.postValue(false)
                response.mapSuccess { responseItems -> responseItems }
            }.either(::handleError, ::setRepositories)
        }
    }

    private fun handleError(failure: Failure) {
        message.postValue(failure.message)
    }

    fun setRepositories(items: List<RepositoryDTO>) {
        val itemsFoUi = getRepositoriesForUi(items)
        repositories.postValue(itemsFoUi)
    }
}