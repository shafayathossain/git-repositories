package com.example.github.repositories

import android.widget.TextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.github.repositories.data.RepositoryDTO
import com.example.github.repositories.main.MainFragment
import com.example.github.repositories.main.MainViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.spy

@LargeTest
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
        testRepositoryLiveData = MutableLiveData(viewModel.getRepositoriesForUi(testResponse).items)
        `when`(viewModel.fetchItems()).then {}
        `when`(viewModel.repositories).thenReturn(testRepositoryLiveData)

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
            .check(matches(hasChildCount(repositories.size)))
    }

    @Test
    fun titlesInTheListIsCorrect() {
        val repositories = viewModel.repositories.getOrAwaitValue()
        for (i in 0 until repositories.size) {
            onView(RecyclerViewMatcher(R.id.news_list).atPositionOnView(i, R.id.title))
                .check(matches(withText(viewModel.getItemNameForList(i, repositories[i]))))

        }
    }

    @Test
    fun testIfDescriptionInTheListTruncatedProperly() {
        val repositories = viewModel.repositories.getOrAwaitValue()
        for (i in 0 until repositories.size) {
            val item = repositories[i]
            val expectedDescriptionLength =
                if (!item.description.isNullOrEmpty() && item.description!!.length > 150) 153
                else (item.description?.length ?: 0)

            onView(RecyclerViewMatcher(R.id.news_list).atPositionOnView(i, R.id.description))
                .check(matches(isTextLength(expectedDescriptionLength)))

            if(expectedDescriptionLength == 153) {
                onView(RecyclerViewMatcher(R.id.news_list).atPositionOnView(i, R.id.description))
                    .check(matches(isTextSuffix("...")))
            }
        }
    }

    @Test
    fun testNavigationBetweenMainAndDetailFragmentWorksProperly() {
        val repositories = viewModel.repositories.getOrAwaitValue()
        for (i in 0 until repositories.size) {
            val item = repositories[i]
            onView(RecyclerViewMatcher(R.id.news_list).atPositionOnView(i, R.id.title))
                .perform(click())
            onView(withId(R.id.detail_fragment_parent)).check(matches(isDisplayed()))
            onView(withId(R.id.title)).check(matches(withText(item.name)))
            pressBack()
            onView(withId(R.id.news_list)).check(matches(isDisplayed()))
        }
    }
}
