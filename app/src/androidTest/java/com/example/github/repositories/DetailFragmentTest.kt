package com.example.github.repositories

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.github.repositories.features.MainActivity
import com.example.github.repositories.features.detail.DetailFragment
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class DetailFragmentTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)
    private val repository = testResponse.items[0]

    @Before
    fun before() {
        mActivityTestRule.scenario.onActivity {
            val bundle = Bundle()
            bundle.putParcelable(DetailFragment.DETAIL_DATA_TAG, repository)
            val fragment = DetailFragment()
            fragment.arguments = bundle
            it.replaceFragment(fragment)
        }
    }

    @Test
    fun testNavigationBetweenDetailAndUserFragmentWorksProperly() {
        onView(withId(R.id.detail)).perform(click())
        onView(withId(R.id.user_fragment_parent)).check(matches(isDisplayed()))
        pressBack()
        onView(withId(R.id.detail_fragment_parent)).check(matches(isDisplayed()))
    }

}
