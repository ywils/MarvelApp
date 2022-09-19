package com.android.marvelapp


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.marvelapp.idling.FetchingIdlingResource
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    private val fetchingIdlingResource = FetchingIdlingResource()

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)


    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(fetchingIdlingResource)
        mActivityScenarioRule.scenario.onActivity { it.setFetcherListener(fetchingIdlingResource) }
    }

    @Test
    fun mainActivityTest() {
        onView(withId(R.id.search)).check(matches(isDisplayed()))
        onView(withText("Sentry, the (Trade Paperback)")).check(matches(isDisplayed()))
        onView(withText("The Story")).check(matches(isDisplayed()))

    }

    @Test
    fun searchTest() {
        val appCompatImageView = onView(
            allOf(
                withId(androidx.appcompat.R.id.search_button),
                isDisplayed()
            )
        )
        appCompatImageView.perform(click())

        val searchAutoComplete = onView(
            allOf(withId(androidx.appcompat.R.id.search_src_text), isDisplayed())
        )
        searchAutoComplete.perform(replaceText("7"), closeSoftKeyboard())

        val searchAutoComplete2 = onView(
            allOf(withId(androidx.appcompat.R.id.search_src_text), withText("7"), isDisplayed())
        )
        searchAutoComplete2.perform(pressImeActionButton())

        val textView = onView(
            allOf(
                withId(R.id.comic_title),
                withText("Art of Marvel Vol. 2 (Hardcover)"),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Art of Marvel Vol. 2 (Hardcover)")))

        // Un-comment to see test fully
        // Thread.sleep(5000)
    }
}
