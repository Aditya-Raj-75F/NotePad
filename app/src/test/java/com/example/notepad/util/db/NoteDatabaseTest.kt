package com.example.notepad.util.db

import android.content.Context
import com.example.notepad.db.NoteDatabase
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class NoteDatabaseTest {
    lateinit var noteDatabase: NoteDatabase
    @Mock
    private lateinit var mockContext: Context
    @Test
    fun test_invoke() {
        MockitoAnnotations.openMocks(this)
        Mockito.`when`(mockContext.applicationContext).thenReturn(mockContext)
        noteDatabase = NoteDatabase(mockContext)
        assertTrue(NoteDatabase.instance!=null) {"Something happened"}
    }
}