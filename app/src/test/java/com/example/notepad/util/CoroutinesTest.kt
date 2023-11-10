package com.example.notepad.util

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Testing Coroutine Utility")
class CoroutinesTest {

    @Test
    fun mainTest() {
        var jobExecuted = false
        Coroutines.main {
            jobExecuted = true
            assertTrue(jobExecuted)
        }
    }
}