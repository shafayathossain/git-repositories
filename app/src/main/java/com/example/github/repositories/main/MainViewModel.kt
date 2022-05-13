package com.example.github.repositories.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.github.repositories.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel() as T
        }
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(GITHUB_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service: GitHubEndpoints = retrofit.create(GitHubEndpoints::class.java)

    val repositories = MutableLiveData<List<RepositoryDTO>>()

    fun fetchItems() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1_000) // This is to simulate network latency, please don't remove!
            val response = service.searchRepositories(QUERY, SORT, ORDER).execute().body()
            repositories.postValue(response?.items)
        }
    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1_000) // This is to simulate network latency, please don't remove!
            val response = service.searchRepositories(QUERY, SORT, ORDER).execute().body()
            repositories.postValue(response?.items)
        }
    }
}