package com.example.github.repositories

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.github.repositories.base.SingleLiveEvent
import com.example.github.repositories.data.RepositoryDTO
import com.example.github.repositories.main.MainFragment
import com.example.github.repositories.main.MainViewModel
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import java.lang.Thread.sleep

@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class MainFragmentTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)
    private lateinit var viewModel: MainViewModel
    private lateinit var testRepositoryLiveData: MutableLiveData<List<RepositoryDTO>>

    @Before
    fun before() {
        testRepositoryLiveData = MutableLiveData(testResponse.items)
        viewModel = mock(MainViewModel::class.java)
        `when`(viewModel.fetchItems())
            .then {}
        `when`(viewModel.repositories).thenReturn(testRepositoryLiveData)
        `when`(viewModel.message).thenReturn(SingleLiveEvent())
    }

    @Test
    fun mainFragmentTest() {

        mActivityTestRule.scenario.onActivity {
            it.replaceFragment(MainFragment().apply {
                viewModelProvider = createFor(this@MainFragmentTest.viewModel)
            })
        }
        sleep(5000)
        val textView = onView(
            allOf(
                withId(R.id.description),
                withText("Square’s meticulous HTTP client for the JVM, Android, and GraalVM."),
                withParent(
                    allOf(
                        withId(R.id.news_container),
                        withParent(withId(R.id.news_list))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Square’s meticulous HTTP client for the JVM, Android, and GraalVM.")))
    }
}
