package com.lloyds.assignment.custom

import android.os.SystemClock
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.lloyds.assignment.R
import com.lloyds.assignment.custom.ui.main.activity.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
public class MainActivityTest {
    @Rule @JvmField
    public var mActivityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun onItemClickOnPlayingNowView()
    {
        SystemClock.sleep(2000)
        onView(withId(R.id.playing_now_rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2,click()))
        SystemClock.sleep(1500)
    }


    @Test
    fun onItemClickOnPopularView()
    {
        SystemClock.sleep(2000)
        onView(withId(R.id.most_popular_rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2,click()))
        SystemClock.sleep(1500)
    }

    @Test
    fun scrollOnPlayingNowView(){
        SystemClock.sleep(2000)
        onView(withId(R.id.playing_now_rv)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                mActivityRule.activity.playing_now_rv.getAdapter()!!.getItemCount() - 1
            )
        )
        SystemClock.sleep(2000)
    }
}
