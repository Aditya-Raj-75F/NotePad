package com.example.notepad.util.db

import com.example.notepad.db.NoteModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Testing NoteModel Data Class")
class NoteModelTest {
    @Test
    @DisplayName("Note instance equality")
    fun testNoteEquality() {
        val note1 = NoteModel("Hello World", "I am Aditya Raj.", null)
        val note2 = NoteModel("Hello World", "I am Aditya Raj.", null)
        assertEquals(note1, note2, "Notes Instance should have their properties matched.")
    }

    @Test
    @DisplayName("Note instance inequality")
    fun testNoteInequality() {
        val note1 = NoteModel("Emoloyee Info", "Aditya Raj", null)
        val note2 = NoteModel("Emoloyee Info", "Abudlla Yusuf Khan", null)
        assertNotEquals(note1, note2, "Notes instances should have different property values.")
    }

    @Test
    @DisplayName("Id check")
    fun testNoteIdDefaultValue() {
        val note1 = NoteModel("Emoloyee Info", "Aditya Raj", null)
        assertEquals(0, note1.id)
    }
}