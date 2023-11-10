package com.example.notepad.ui.db

import com.example.notepad.db.NoteModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Testing NoteModel Data Class")
class NoteModelTest {

        private lateinit var noteActual : NoteModel
        @BeforeEach
        fun init() {
            noteActual = NoteModel("Hello World", "It is nice to meet you.")
        }

        @Test
        @DisplayName("Note instance equality")
        fun testNoteEquality() {
            val noteExpected = NoteModel("Hello World", "It is nice to meet you.")
            assertEquals(noteExpected, noteActual) { "Notes Instance should have their properties matched." }
        }

        @Test
        @DisplayName("Note instance inequality")
        fun testNoteInequality() {
            val noteExpected = NoteModel("Hello World", "I am honored to address you all today.")
            assertNotEquals(noteExpected, noteActual) { "Notes instances should have different property values." }
        }

        @Test
        @DisplayName("Note ID validation")
        fun testNoteIdentifier() {
            assertEquals(0, noteActual.id) { "Default ID of note should be 0" }
        }
}