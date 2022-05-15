package com.example.github.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.MediumTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.github.repositories.data.local.LocalDataStore
import com.example.github.repositories.data.model.RepositoryDTO
import com.example.github.repositories.features.MainActivity
import com.example.github.repositories.features.main.MainFragment
import com.example.github.repositories.features.main.MainViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.spy

@MediumTest
@RunWith(AndroidJUnit4ClassRunner::class)
class MainFragmentTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)
    private lateinit var viewModel: MainViewModel
    private lateinit var testRepositoryLiveData: MutableLiveData<List<RepositoryDTO>>

    @Before
    fun before() {
        viewModel = spy(MainViewModel())
        viewModel.showLoader.postValue(false)
        viewModel.repositories.postValue(viewModel.getRepositoriesForUi(testResponse.items))
        `when`(viewModel.fetchItems()).then {}
        mActivityTestRule.scenario.onActivity {
            it.replaceFragment(MainFragment().apply {
                viewModelProvider = createFor(this@MainFragmentTest.viewModel)
            })
        }
    }

    @Test
    fun testRecyclerViewItemCountIsCorrect() {
        val repositories = viewModel.repositories.getOrAwaitValue()
        onView(withId(R.id.news_list))
            .check(RecyclerViewItemCountAssertion.withItemCount(repositories.size))
    }

    @Test
    fun titlesInTheListIsCorrect() {
        val repositories = viewModel.repositories.getOrAwaitValue()
        for (i in repositories.indices) {
            onView(RecyclerViewMatcher(R.id.news_list).atPositionOnView(i, R.id.title))
                .check(matches(withText(viewModel.getItemNameForList(i, repositories[i]))))
        }
    }

    @Test
    fun testIfDescriptionInTheListTruncatedProperly() {
        val repositories = viewModel.repositories.getOrAwaitValue()
        for (i in repositories.indices) {
            val item = repositories[i]
            val expectedDescriptionLength =
                if (!item.description.isNullOrEmpty() && item.description!!.length > 150) 153
                else (item.description?.length ?: 0)

            onView(RecyclerViewMatcher(R.id.news_list).atPositionOnView(i, R.id.description))
                .check(matches(isTextLength(expectedDescriptionLength)))

            if (expectedDescriptionLength == 153) {
                onView(RecyclerViewMatcher(R.id.news_list).atPositionOnView(i, R.id.description))
                    .check(matches(isTextSuffix("...")))
            }
        }
    }

    @Test
    fun testNavigationBetweenMainAndDetailFragmentWorksProperly() {
        val repositories = viewModel.repositories.getOrAwaitValue()
        for (i in repositories.indices) {
            val item = repositories[i]
            onView(RecyclerViewMatcher(R.id.news_list).atPositionOnView(i, R.id.title))
                .perform(click())
            onView(withId(R.id.detail_fragment_parent)).check(matches(isDisplayed()))
            onView(withId(R.id.title)).check(matches(withText(item.name)))
            pressBack()
            onView(withId(R.id.news_list)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun testIfMarkedAsBookmarkProperly() {
        val repositories = viewModel.repositories.getOrAwaitValue()
        for (i in 0 until repositories.size * 2) {
            val itemIndex = i / 2
            val item = repositories[itemIndex]
            checkIfBookmarkDrawableCorrect(item, itemIndex)
            onView(
                RecyclerViewMatcher(R.id.news_list)
                    .atPositionOnView(itemIndex, R.id.title)
            )
                .perform(click())
            onView(withId(R.id.iv_bookmark)).perform(click())
            pressBack()
            checkIfBookmarkDrawableCorrect(item, itemIndex)
        }
    }

    private fun checkIfBookmarkDrawableCorrect(
        item: RepositoryDTO,
        index: Int
    ) {
        val bookmarked = getIsBookmarked(item.id)
        onView(RecyclerViewMatcher(R.id.news_list).atPositionOnView(index, R.id.iv_bookmark))
            .check(matches(drawableIsCorrect(getDrawableIdForBookmarkStatus(bookmarked))))
    }

    private fun getDrawableIdForBookmarkStatus(isBookmarked: Boolean): Int {
        return if (isBookmarked) R.drawable.baseline_bookmark_black_24
        else R.drawable.baseline_bookmark_border_black_24
    }

    private fun getIsBookmarked(repositoryId: Int) =
        LocalDataStore.instance.getBookmarks().contains(repositoryId)
}
