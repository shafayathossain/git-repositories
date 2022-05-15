package com.example.github.repositories

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.annotation.UiThreadTest
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.example.github.repositories.RecyclerViewItemCountAssertion.Companion.withItemCount
import com.example.github.repositories.main.MainFragment
import com.example.github.repositories.main.MainViewModel
import com.example.github.repositories.user.UserFragment
import com.example.github.repositories.user.UserViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.spy

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
        val user = viewModel.user.getOrAwaitValue()
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