package com.example.github.repositories


import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.github.repositories.data.LocalDataStore
import com.example.github.repositories.detail.DetailFragment
import com.example.github.repositories.main.MainFragment
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.hamcrest.TypeSafeMatcher
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
    private lateinit var preferences: SharedPreferences
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
