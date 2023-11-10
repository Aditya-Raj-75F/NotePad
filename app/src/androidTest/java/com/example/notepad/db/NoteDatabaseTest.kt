package com.example.notepad.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Testing Note Database")
class NoteDatabaseTest {
    @Test
    @DisplayName("DAO Retrieval")
    fun getNoteDaoRetrieval() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(context, TestNoteDatabase::class.java).allowMainThreadQueries().build()
        assertTrue(db.getNotesDao() is NoteDao)
    }
}