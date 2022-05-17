package com.app.githubsearch.components.details

import android.content.Context
import androidx.core.os.bundleOf
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.app.githubsearch.R
import com.app.githubsearch.di.modules.RepositoryModule
import com.app.githubsearch.util.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(RepositoryModule::class)
class DetailFragmentTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @Test
    fun test_when_Display_data_from_repo_display() {
        launchFragmentInHiltContainer<DetailFragment>(fragmentArgs = bundleOf("username" to "Ahmed"))
        Espresso.onView(ViewMatchers.withId(R.id.title)).check(ViewAssertions.matches(ViewMatchers.withText("ahmed")))
        Espresso.onView(ViewMatchers.withId(R.id.email)).check(ViewAssertions.matches(ViewMatchers.withText(ApplicationProvider.getApplicationContext<Context>().getString(R.string.email_label, "ahmedcs2600@gmail.com"))))
        Espresso.onView(ViewMatchers.withId(R.id.bio)).check(ViewAssertions.matches(ViewMatchers.withText(ApplicationProvider.getApplicationContext<Context>().getString(R.string.bio_label, "Test Bio"))))
        Espresso.onView(ViewMatchers.withId(R.id.location)).check(ViewAssertions.matches(ViewMatchers.withText(ApplicationProvider.getApplicationContext<Context>().getString(R.string.location_label, "Karachi"))))
        Espresso.onView(ViewMatchers.withId(R.id.followers_url)).check(ViewAssertions.matches(ViewMatchers.withText(ApplicationProvider.getApplicationContext<Context>().getString(R.string.followers_url_label, "google.com"))))
        Espresso.onView(ViewMatchers.withId(R.id.following_url)).check(ViewAssertions.matches(ViewMatchers.withText(ApplicationProvider.getApplicationContext<Context>().getString(R.string.following_url_label, "google.com"))))
        Espresso.onView(ViewMatchers.withId(R.id.repos_url)).check(ViewAssertions.matches(ViewMatchers.withText(ApplicationProvider.getApplicationContext<Context>().getString(R.string.repos_url_label, "google.com"))))
        Espresso.onView(ViewMatchers.withId(R.id.events_url)).check(ViewAssertions.matches(ViewMatchers.withText(ApplicationProvider.getApplicationContext<Context>().getString(R.string.events_url_label, "google.com"))))
    }
}