package com.example.github.repositories

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.annotation.UiThreadTest
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.github.repositories.RecyclerViewItemCountAssertion.Companion.withItemCount
import com.example.github.repositories.features.MainActivity
import com.example.github.repositories.features.user.UserFragment
import com.example.github.repositories.features.user.UserViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class UserFragmentTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)
    private lateinit var viewModel: UserViewModel
    private val repository = testResponse.items[0]

    @Before
    fun before() {
        viewModel = UserViewModel()
        mActivityTestRule.scenario.onActivity {
            val bundle = Bundle()
            bundle.putParcelable(UserFragment.USER_DATA_TAG, repository.owner)
            val fragment = UserFragment()
            fragment.viewModelProviderFactor = createFor(viewModel)
            fragment.arguments = bundle
            it.replaceFragment(fragment)
        }
    }

    @Test
    @UiThreadTest
    fun testUserDataLoadedProperly() {
        val user = viewModel.user.getOrAwaitValue(4)
        val repositories = viewModel.repositories.getOrAwaitValue()
        assert(user.login == repository.owner.login)
        assert(user.id.toInt() == repository.owner.id)
        run {
            val viewInteraction =  onView(ViewMatchers.withId(R.id.list))
            GlobalScope.launch {
                viewInteraction.check(withItemCount(repositories.size))
            }
        }
    }
}