package com.example.github.repositories

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

fun isTextLength(length: Int): TypeSafeMatcher<View> {
    return object : TypeSafeMatcher<View>() {
        override fun matchesSafely(item: View): Boolean {
            return (item as TextView).length() == length
        }

        override fun describeTo(description: Description) {
            description.appendText("isTextLength")
        }
    }
}

fun isTextSuffix(suffix: String): TypeSafeMatcher<View> {
    return object : TypeSafeMatcher<View>() {
        override fun matchesSafely(item: View): Boolean {
            return (item as TextView).text.endsWith(suffix)
        }

        override fun describeTo(description: Description) {
            description.appendText("isTextSuffix")
        }
    }
}