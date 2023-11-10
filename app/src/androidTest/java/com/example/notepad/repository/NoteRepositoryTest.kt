package com.example.notepad.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.notepad.db.NoteModel
import com.example.notepad.db.TestNoteDatabase
import com.example.notepad.ui.testUtils.getOrAwaitValue
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Testing Repository Class")
class NoteRepositoryTest {
    private lateinit var noteRepository: NoteRepository
    private lateinit var title : String
    private lateinit var content  : String

    @BeforeEach
    fun setup() {
        title = "Hello World"
        content = "It's nice to see you all."
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(context, TestNoteDatabase::class.java).allowMainThreadQueries().build()
        val noteDao = db.getNotesDao()
        noteRepository = NoteRepository(noteDao)
    }

    @Test
    @DisplayName("Inserting New Note and Fetching that note")
    fun addAndFetchNoteTest() = runTest{
        noteRepository.addNote(title, content)
        assertEquals(NoteModel(title, content), noteRepository.getAllNotes().getOrAwaitValue()[0]) {"Note details are not matching"}
    }

    @Test
    @DisplayName("Inserting a note and then updating it")
    fun addAndUpdateNoteTest() = runTest {
        noteRepository.addNote(title, content)
        title = "Hello Aditya"
        content = "How are you doing"
        noteRepository.updateNote(1, title, content)
        assertEquals(NoteModel(title, content), noteRepository.getAllNotes().getOrAwaitValue()[0]) {"Updated values of notes are not being reflected"}
    }

    @Test
    @DisplayName("Finding a note by its ID and then deleteing it")
    fun findByIDAndDeleteNoteTest() = runTest {
        noteRepository.addNote(title, content)
        val note = noteRepository.getNoteById(1)
        noteRepository.deleteNote(note!!.id)
        assertEquals(NoteModel(title, content), note)
        assertTrue(noteRepository.getNoteById(1)==null) {"The note was note deleted"}
    }

    @Test
    @DisplayName("Deleting multiple notes by their ID")
    fun deleteMultipleNotesTest() = runTest {
        noteRepository.addNote(title, content)
        title = "Hello Aditya"
        content = "How are you doing?"
        noteRepository.addNote(title, content)
        title = "Hello Yusuf"
        content = "Did you arrive early today?"
        noteRepository.addNote(title, content)
        noteRepository.deleteNotes(listOf(1,3))
        assertEquals(1, noteRepository.getAllNotes().getOrAwaitValue().size) {"All specified notes were not deleted"}
        assertTrue(noteRepository.getNoteById(2)!=null) {"Specified indexed note not deleted"}
    }
}