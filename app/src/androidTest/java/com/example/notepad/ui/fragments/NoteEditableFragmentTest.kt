package com.example.notepad.ui.fragments

import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withInputType
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.notepad.R
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class NoteEditableFragmentTest {
    lateinit var scenario: FragmentScenario<NoteEditableFragment>
    private lateinit var navController: NavController
    @BeforeEach
    fun setup() {
        scenario = launchFragmentInContainer(
            themeResId = R.style.Theme_NotePad
        )
    }
    @Nested
    inner class VisibilityCheck {
        @Test
        fun test_isNoteEditableFragmentVisible() {
            onView(withId(R.id.addOrModifyNoteLayout)).check(matches(isDisplayed()))
        }

        @Test
        fun test_isLinearLayoutHeaderVisible() {
            onView(withId(R.id.header)).check(matches(isDisplayed()))
        }

        @Test
        fun test_isHeaderVisible() {
            onView(withId(R.id.addOrUpdateNote)).check(matches(isDisplayed()))
        }

        @Test
        fun test_isSaveNoteButtonVisible() {
            onView(withId(R.id.saveNoteButton)).check(matches(isDisplayed()))
        }

        @Test
        fun test_isTitleFieldVisible() {
            onView(withId(R.id.editTitle)).check(matches(isDisplayed()))
        }

        @Test
        fun test_isContentFieldVisible() {
            onView(withId(R.id.editContent)).check(matches(isDisplayed()))
        }

        @Test
        fun test_isDeleteFABVisible() {
            onView(withId(R.id.deleteButton)).check(matches(isDisplayed()))
        }
    }

    @Nested
    inner class TextPresenceCheck {
        @Test
        fun test_isHeaderTextVisible() {
            onView(withId(R.id.addOrUpdateNote)).check(matches(withText(R.string.add_note)))
        }

        @Test
        fun test_isEditTitleHintVisible() {
            onView(withId(R.id.editTitle)).check(matches(withHint(R.string.note_title_hint)))
        }

        @Test
        fun test_isEditContentHintVisible() {
            onView(withId(R.id.editContent)).check(matches(withHint(R.string.note_content_hint)))
        }
    }

    @Nested
    inner class TestInViewInteractions {
        @Test
        fun test_isTitleEditTextEditable() {
            onView(withId(R.id.editTitle)).perform(typeText("Hello World")).check(matches(withText("Hello World")))
        }

        @Test
        fun test_isContentEditTextEditable() {
            onView(withId(R.id.editContent)).perform(typeText("It is nice to meet you all")).check(matches(withText("It is nice to meet you all")))
        }

        @Test
        fun test_isContentEditText_supportMultiLine() {
            onView(withId(R.id.editContent)).check(matches(withInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_CLASS_TEXT)))
        }

        @Test
        fun test_multiLineText_inContentEditText() {
            onView(withId(R.id.editContent)).perform(typeText("It is nice to meet you all.\n Please introduce yourselves one by one"))
                .check(matches(withText("It is nice to meet you all.\n" +
                    " Please introduce yourselves one by one")))
        }
    }

    @Nested
    inner class TestDrawablePresence {
        @Test
        fun test_isOrangeBorderVisible_onTitleEditText() {
            onView(withId(R.id.editTitle))
                .check(matches(object: TypeSafeMatcher<View>() {
                    override fun matchesSafely(item: View?): Boolean {
                        if(item !is EditText) {
                            return false
                        }
                        val backgroundDrawable = item.background
                        val expectedDrawable = ContextCompat.getDrawable(item.context, R.drawable.border)
                        return backgroundDrawable.constantState == expectedDrawable?.constantState
                    }

                    override fun describeTo(description: Description?) {
                        description?.appendText("with background drawable")
                        description?.appendValue(R.drawable.border)
                    }
                }))
        }

        @Test
        fun test_isOrangeBorderVisible_onContentEditText() {
            onView(withId(R.id.editContent))
                .check(matches(object: TypeSafeMatcher<View>() {
                    override fun matchesSafely(item: View?): Boolean {
                        if(item !is EditText) {
                            return false
                        }
                        val backgroundDrawable = item.background
                        val expectedDrawable = ContextCompat.getDrawable(item.context, R.drawable.border)
                        return backgroundDrawable.constantState == expectedDrawable?.constantState
                    }

                    override fun describeTo(description: Description?) {
                        description?.appendText("with background drawable")
                        description?.appendValue(R.drawable.border)
                    }
                }))
        }

//        @Test
//        fun test_isDoneIconVisible_onSaveNoteButton() {
//            onView(withId(R.id.saveNoteButton))
//                .check(matches(object: TypeSafeMatcher<View>() {
//                    override fun matchesSafely(item: View?): Boolean {
//                        if(item !is ImageView) {
//                            return false
//                        }
//                        val imageView = item as ImageView
//                        val actualDrawable = imageView.drawable
//                        val expectedDrawable = ContextCompat.getDrawable(item.context, R.drawable.baseline_done_24)
//                        return actualDrawable.constantState == expectedDrawable?.constantState
//                    }
//
//                    override fun describeTo(description: Description?) {
//                        description?.appendText("with src drawable")
//                        description?.appendValue(R.drawable.baseline_done_24)
//                    }
//                }))
//        }

        @Test
        @Disabled
        fun test_isDoneIconVisible_onSaveNoteButton() {
            onView(withId(R.id.saveNoteButton))
                .check(matches(object : TypeSafeMatcher<View>() {
                    override fun matchesSafely(item: View?): Boolean {
                        if (item !is ImageView) {
                            return false
                        }
                        val imageView = item as ImageView
                        val actualDrawableId = imageView.drawable.constantState?.toString()
                        val expectedDrawableId = ContextCompat.getDrawable(item.context, R.drawable.baseline_done_24)?.constantState?.toString()
                        Log.d("DrawableTest", "Actual Drawable ID: $actualDrawableId")
                        Log.d("DrawableTest", "Expected Drawable ID: $expectedDrawableId")
                        return actualDrawableId == expectedDrawableId
                    }

                    override fun describeTo(description: Description?) {
                        description?.appendText("with src drawable")
                        description?.appendValue(R.drawable.baseline_done_24)
                    }
                }))
        }
        @Nested
        inner class TestButtonClicks {
            @Test
            @Disabled
            fun test_saveNoteButtonClick() {
                onView(withId(R.id.saveNoteButton)).perform(click())
            }
        }


    }
}