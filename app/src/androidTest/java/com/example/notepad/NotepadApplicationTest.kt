package com.example.notepad

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class NotepadApplicationTest {
    @Test
    fun test_constructor() {
        var app = NotepadApplication()
        assertThrows<UninitializedPropertyAccessException> { app.appContainer }
    }
}