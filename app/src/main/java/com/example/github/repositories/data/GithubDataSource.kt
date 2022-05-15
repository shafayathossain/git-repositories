package com.example.github.repositories.data

import com.example.github.repositories.data.local.LocalDataStore
import com.example.github.repositories.data.model.RepositoryDTO
import com.example.github.repositories.data.model.Response
import com.example.github.repositories.data.model.UserDTO
import com.example.github.repositories.data.network.GitHubEndpoints
import com.example.github.repositories.data.network.network_utils.GITHUB_URL
import com.example.github.repositories.data.network.network_utils.ORDER
import com.example.github.repositories.data.network.network_utils.QUERY
import com.example.github.repositories.data.network.network_utils.SORT
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GithubDataSource {
    private val retrofit = Retrofit.Builder()
        .baseUrl(GITHUB_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service: GitHubEndpoints = retrofit.create(GitHubEndpoints::class.java)

    suspend fun fetchItems(): Response {
        return service.searchRepositories(QUERY, SORT, ORDER)
    }

    suspend fun fetchUser(username: String): UserDTO {
        return service.getUser(username)
    }

    suspend fun fetchUserRepositories(url: String): List<RepositoryDTO> {
        return service.getUserRepositories(url)
    }

    fun getIsBookmarked(repositoryId: Int): Boolean {
        return LocalDataStore.instance.getBookmarks().contains(repositoryId)
    }

    fun toggleBookmark(repositoryId: Int): Boolean {
        val isBookmarked = LocalDataStore.instance.getBookmarks().contains(repositoryId)
        LocalDataStore.instance.bookmarkRepo(repositoryId, !isBookmarked)

        return getIsBookmarked(repositoryId)
    }
}