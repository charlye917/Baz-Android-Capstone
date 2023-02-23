package com.carteagal.baz_android.espresso

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.carteagal.baz_android.R
import com.carteagal.baz_android.presentation.adapter.BooksAdapter
import com.carteagal.baz_android.presentation.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class CryptoViewTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun loadMainCheck(){
        activityScenarioRule.scenario
        onView(ViewMatchers.withId(R.id.rv_books))
            .perform(RecyclerViewActions.scrollToPosition<BooksAdapter.ViewHolder>(5))
    }
}