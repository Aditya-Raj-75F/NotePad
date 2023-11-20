package com.example.notepad.ui.fragments

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import com.example.notepad.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.job
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.coroutines.ContinuationInterceptor

class BaseFragmentTest {
    lateinit var scenario: FragmentScenario<NoteEditableFragment>

    @BeforeEach
    fun setup() {
        scenario = launchFragmentInContainer(
            themeResId = R.style.Theme_NotePad
        )
    }
    @Test
    fun test_coroutineScope() {
        scenario.withFragment {
            assertEquals(Dispatchers.Main, this.coroutineContext[ContinuationInterceptor])
            assertTrue(this.coroutineContext.job.isActive)
            assertFalse(this.coroutineContext.job.isCancelled)
            assertNotEquals(Dispatchers.IO, this.coroutineContext[ContinuationInterceptor])
            this.onDestroy()
        }
    }
}