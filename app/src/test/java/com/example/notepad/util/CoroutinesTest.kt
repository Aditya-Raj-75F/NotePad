package com.example.notepad.util

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Testing Coroutine Utility")
class CoroutinesTest {

    @Test
    fun mainTest() = runTest {
        var jobExecuted= false
        Coroutines.main {
            jobExecuted = true
        }.join()
        assertTrue(jobExecuted)
    }
}