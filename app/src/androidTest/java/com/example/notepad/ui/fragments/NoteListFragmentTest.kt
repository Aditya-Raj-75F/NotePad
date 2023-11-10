package com.example.notepad.ui.fragments

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.notepad.R
import org.hamcrest.CoreMatchers.not
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class NoteListFragmentTest {
    lateinit var scenario: FragmentScenario<NoteListFragment>

    @BeforeEach
    fun setup() {
        scenario = launchFragmentInContainer(
            themeResId = R.style.Theme_NotePad
        )
    }

    @Nested
    inner class TestViewVisibility{
        @Test
        fun test_isFragmentVisible() {
            onView(withId(R.id.noteListLayout)).check(matches(isDisplayed()))
        }

        @Test
        fun test_isHeaderLayoutVisible() {
            onView(withId(R.id.header_container)).check(matches(isDisplayed()))
        }

        @Test
        fun test_isNotepadLabelVisible() {
            onView(withId(R.id.notepadLabel)).check(matches(isDisplayed()))
        }

        @Test
        fun test_isAddNewNoteButtonVisible() {
            onView(withId(R.id.addNoteButton)).check(matches(isDisplayed()))
        }

        @Test
        fun test_isNoteList_recyclerViewVisible() {
            onView(withId(R.id.noteList)).check(matches(isDisplayed()))
        }

        @Test
        fun test_isDelete_floatingActionButtonGone() {
            onView(withId(R.id.multiSelectMenuButton)).check(matches(not(isDisplayed())))
        }

        @Test
        fun test_isCancelAllSelection_floatingActionButtonGone() {
            onView(withId(R.id.cancelSelectedItems)).check(matches(not(isDisplayed())))
        }
    }

    @Nested
    inner class TestTextVisibility {
        @Test
        fun test_isLabelVisible() {
            onView(withId(R.id.notepadLabel)).check(matches(withText("NotePad")))
        }
    }
}