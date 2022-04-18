package demo.currency.myapplication.utils

import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


fun childAtPosition(
    parentMatcher: Matcher<View>, position: Int
): Matcher<View> {

    return object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("Child at position $position in parent ")
            parentMatcher.describeTo(description)
        }

        public override fun matchesSafely(view: View): Boolean {
            val parent = view.parent
            return parent is ViewGroup && parentMatcher.matches(parent)
                    && view == parent.getChildAt(position)
        }
    }
}


fun atPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> {
    return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has item at position $position: ")
            itemMatcher.describeTo(description)
        }

        override fun matchesSafely(view: RecyclerView): Boolean {
            val viewHolder = view.findViewHolderForAdapterPosition(position)
                ?: return false
            return itemMatcher.matches(viewHolder.itemView)
        }
    }
}

fun withTextColor(colorRes: Int): Matcher<View> {
    return object : BoundedMatcher<View, TextView>(TextView::class.java) {
        override fun matchesSafely(item: TextView): Boolean {
            val context = item.context
            val expectedColor = if (Build.VERSION.SDK_INT <= 22) {
                context?.resources?.getColor(colorRes)
            } else {
                context?.getColor(colorRes)
            }
            return item.currentTextColor == expectedColor
        }

        override fun describeTo(description: Description) {
            description.appendText("with text color: ")
        }
    }
}