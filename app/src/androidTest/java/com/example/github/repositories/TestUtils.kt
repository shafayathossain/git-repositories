package com.example.github.repositories

import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
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

fun drawableIsCorrect(@DrawableRes drawableResId: Int): Matcher<View> {
    return object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("with drawable from resource id: ")
            description.appendValue(drawableResId)
        }

        override fun matchesSafely(target: View?): Boolean {
            if (target !is ImageView) {
                return false
            }
            if (drawableResId < 0) {
                return target.drawable == null
            }
            val expectedDrawable = ContextCompat.getDrawable(target.context, drawableResId)
                ?: return false

            val bitmap = (target.drawable as BitmapDrawable).bitmap
            val otherBitmap = (expectedDrawable as BitmapDrawable).bitmap
            return bitmap.sameAs(otherBitmap)
        }
    }
}