package com.app.githubsearch.components.home

import android.content.Context
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.app.githubsearch.R
import com.app.githubsearch.components.home.adapter.viewholder.HomeViewHolder
import com.app.githubsearch.di.modules.RepositoryModule
import com.app.githubsearch.util.clickChildViewWithId
import com.app.githubsearch.util.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(RepositoryModule::class)
class HomeFragmentTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @Test
    fun test_when_Display_data_from_repo_displayed() {
        launchFragmentInHiltContainer<HomeFragment>()
        scrollAtAndCheckTestVisible(0, "ahmed")
        scrollAtAndCheckTestVisible(0, ApplicationProvider.getApplicationContext<Context>().getString(R.string.score_label, "5"))
        scrollAtAndCheckTestVisible(1, "jake")
        scrollAtAndCheckTestVisible(1, ApplicationProvider.getApplicationContext<Context>().getString(R.string.score_label, "10"))
    }

    @Test
    fun test_click_button_navigate_to_detail_screen_with_arguments() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        launchFragmentInHiltContainer<HomeFragment> {
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition<HomeViewHolder>(0, clickChildViewWithId(R.id.parent_card)))
        MatcherAssert.assertThat(navController.currentDestination?.id, CoreMatchers.`is`(R.id.detailFragment))
        MatcherAssert.assertThat(navController.backStack.last().arguments?.get("username"), CoreMatchers.`is`("ahmed"))
    }

    private fun scrollAtAndCheckTestVisible(position: Int, text: String) {
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view))
            .perform(
                RecyclerViewActions
                    .scrollToPosition<HomeViewHolder>(position))
        Espresso.onView(ViewMatchers.withText(text))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}