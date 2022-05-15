package com.example.github.repositories

import com.example.github.repositories.base.BaseViewModel
import com.example.github.repositories.data.model.OwnerDTO
import com.example.github.repositories.data.model.RepositoryDTO
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import kotlin.math.min
import kotlin.random.Random

class TestBaseViewModel {

    @Test
    fun testPopulateRepositoryList() {
        val viewModel = BaseViewModel()
        val repositoryList = mutableListOf<RepositoryDTO>()
        for (i in 0 until 10) {
            val owner = mock(OwnerDTO::class.java)
            repositoryList.add(
                RepositoryDTO(
                    id = i,
                    name = "",
                    owner = owner,
                    stargazers_count = Random.nextInt(999999)
                )
            )
        }
        assertPopulatedListIsCorrect(repositoryList, viewModel)

        for (i in 10 until 30) {
            val owner = mock(OwnerDTO::class.java)
            repositoryList.add(
                RepositoryDTO(
                    id = i,
                    name = "",
                    owner = owner,
                    stargazers_count = Random.nextInt(999999)
                )
            )
        }
        assertPopulatedListIsCorrect(repositoryList, viewModel)
    }

    private fun assertPopulatedListIsCorrect(
        repositoryList: MutableList<RepositoryDTO>,
        viewModel: BaseViewModel
    ) {
        val sortedList = repositoryList
            .sortedBy { it.stargazers_count }
            .subList(0, min(repositoryList.size, 20))
            .reversed()
        val populatedList = viewModel.populateRepositoryList(repositoryList)
        assertEquals(populatedList, sortedList)
        assert(populatedList.size <= MAX_ITEM_COUNT_IN_REPOSITORY_LIST)
    }

}