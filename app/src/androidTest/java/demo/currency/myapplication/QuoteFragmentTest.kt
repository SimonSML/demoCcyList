package demo.currency.myapplication

import android.content.Context
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.reflect.TypeToken
import demo.currency.myapplication.DemoActivityTest.Companion.hideKeyboard
import demo.currency.myapplication.model.CurrencyQuote
import demo.currency.myapplication.utils.readJsonObjectFromAssetFile
import demo.currency.myapplication.utils.withTextColor
import org.hamcrest.Matchers.not
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class QuoteFragmentTest {

    private val instrumentationContext: Context =
        InstrumentationRegistry.getInstrumentation().context

    private val quoteFromListFragment: CurrencyQuote =
        instrumentationContext.readJsonObjectFromAssetFile(
            "quoteInfo.json",
            object : TypeToken<CurrencyQuote>() {}.type
        )

    @Test
    fun testViewAppeared() {
        val scenario = launchFragmentInContainer(
            themeResId = R.style.AppTheme,
            initialState = Lifecycle.State.RESUMED,
            fragmentArgs = bundleOf("key_quote_info" to quoteFromListFragment)
        ) {
            QuoteFragment.newInstance(quoteFromListFragment)
        }

        onView(withId(R.id.top_bar)).check(matches(withText("Trade")))
        onView(withId(R.id.ticker)).check(matches(withText("BTC")))
        onView(withId(R.id.name)).check(matches(withText("BitCoin")))
        onView(withId(R.id.currency)).check(matches(withText("Currency in USD")))
        onView(withId(R.id.price)).check(matches(withText("123.456")))
        onView(withId(R.id.price_change)).check(matches(withText("18.234 (2.232)"))).check(
            matches(
                withTextColor(R.color.stock_positive)
            )
        )
        onView(withId(R.id.btn_preview)).check(matches(not(isEnabled())))
        onView(withId(R.id.input_price)).perform(click(), typeText("1234"))
        hideKeyboard()
        onView(withText("You choose to Buy @$1,234")).check(doesNotExist())
        onView(withId(R.id.btn_preview)).check(matches(isEnabled())).perform(click())
        onView(withText("You choose to BUY @$1,234")).check(matches(isDisplayed()))
    }
}