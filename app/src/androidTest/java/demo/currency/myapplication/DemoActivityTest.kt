package demo.currency.myapplication


import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import demo.currency.myapplication.utils.atPosition
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class DemoActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = activityScenarioRule<DemoActivity>()

    @Test
    fun demoActivityTest() {
        val loadDataButton = onView(
            allOf(
                withId(R.id.btn_load_data), withText("LOAD DATA"),
                withParent(withId(R.id.layout_buttons)),
                isDisplayed()
            )
        )
        loadDataButton.check(matches(isDisplayed())).check(matches(isEnabled()))

        onView(withId(R.id.btn_sort)).check(matches(isDisplayed())).check(matches(not(isEnabled())))

        loadDataButton.perform(click())

        onView(withId(R.id.recyclerview))
            .check(matches(atPosition(0, hasDescendant(withText("Bitcoin Cash")))))

        onView(withId(R.id.recyclerview))
            .check(
                matches(
                    atPosition(
                        1, hasDescendant(
                            allOf(
                                withId(R.id.tv_name),
                                withText("Binance Coin")
                            )
                        )
                    )
                )
            )


        onView(withId(R.id.recyclerview)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        onView(withId(R.id.top_bar)).check(matches(withText("Trade")))
        onView(withId(R.id.input_price)).perform(click(), typeText("12345"))
        hideKeyboard()
        onView(withId(R.id.btn_preview)).perform(click())
        onView(withText("You choose to BUY @$12,345")).check(matches(isDisplayed()))

    }

    companion object {
        fun hideKeyboard() {
            onView(isRoot()).perform(ViewActions.closeSoftKeyboard())
        }
    }

}
