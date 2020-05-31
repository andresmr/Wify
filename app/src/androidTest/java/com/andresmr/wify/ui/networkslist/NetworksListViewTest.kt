package com.andresmr.wify.ui.networkslist

import androidx.room.Room
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.andresmr.wify.R
import com.andresmr.wify.data.db.WifiRoomDatabase
import com.andresmr.wify.ui.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NetworksListViewTest {

    private lateinit var database: WifiRoomDatabase

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, WifiRoomDatabase::class.java).build()
        database.wifiDao().deleteAll()
        activityRule.launchActivity(null)
    }

    @Test
    fun testAddNetworkButton() {
        onView(withId(R.id.floating_action_button)).perform(click())
        onView(withId(R.id.available_networks_title)).check(matches(withText("Select available network")))
        onView(withId(R.id.available_networks_list)).check(matches(isDisplayed()))
    }
}
