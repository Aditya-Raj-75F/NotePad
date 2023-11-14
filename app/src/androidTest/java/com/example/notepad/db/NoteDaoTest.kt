package com.example.notepad.db

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.notepad.ui.testUtils.getOrAwaitValue
import com.example.notepad.util.Coroutines
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
    class NoteDaoTest {

        private lateinit var noteDao: NoteDao
        private lateinit var db: TestNoteDatabase

        @BeforeEach
        fun setup() {
            val context = ApplicationProvider.getApplicationContext<Context>()
            db = Room.inMemoryDatabaseBuilder(context, TestNoteDatabase::class.java).allowMainThreadQueries().build()
            noteDao = db.getNotesDao()
        }

        @AfterEach
        fun tearDown() {
            db.close()
        }

        @Test
        fun insertAndGetAllNotes() = runTest {
            val note = NoteModel(title = "Test Note", content = "This is a test note")
            Log.d("USER_TEST","DB check: ${db}, ${db.isOpen}")

            noteDao.addNote(note)
            noteDao.addNote(note)
            val allNotes = noteDao.getAllNotes().getOrAwaitValue()
            Assertions.assertEquals(2, allNotes.size) {"The numeber of notes inserted and the size of the notes table should match"}
            Assertions.assertEquals(note, allNotes[1])  {"The note inserted should remain the same after insertion."}
        }

        @Test
        fun insertAndDeleteNote() = runTest {
            val note = NoteModel(title = "Test Note", content = "This is a test note")
            noteDao.addNote(note)
            note.id = 1
            db.runInTransaction {
                Coroutines.main { noteDao.deleteNote(note) }
            }
            val allNotes = noteDao.getAllNotes().getOrAwaitValue()
            Assertions.assertTrue(allNotes.isEmpty()) {"The table should be empty after deleting the only inserted note."}
        }

        @Test
        fun findElementByIdTest() = runTest {
            val note1 = NoteModel("Aditya Raj", "Hello Everybody")
            val note2 = NoteModel("Abdulla Yusuf", "Good morning everyone")
            noteDao.addNote(note1)
            noteDao.addNote(note2)
            Assertions.assertEquals(note2, noteDao.getNoteById(2)) {"The position of the note inserted is not matching."}
        }

        @Test
        fun updateNote() = runTest{
            val note = NoteModel(title = "Test Note", content = "This is a test note")
            noteDao.addNote(note)
            val updatedNote = note.copy(title = "Updated Note", content = "This is an updated note")
            updatedNote.id = 1
            noteDao.updateNote(updatedNote)
            val allNotes = noteDao.getAllNotes().getOrAwaitValue()
            Assertions.assertEquals(1, allNotes.size) {"The notes sent for updation has instead been inserted"}
            Assertions.assertEquals(updatedNote, allNotes[0]) {"The notes has not been udpated successfully."}
        }

        @Test
        fun deleteMultipleNotesTest() = runTest {
            val note1 = NoteModel(title = "Test Note", content = "This is a test note")
            val note2 = NoteModel("Aditya Raj", "Hello Everybody")
            val note3 = NoteModel("Abdulla Yusuf", "Good morning everyone")
            noteDao.addNote(note1)
            noteDao.addNote(note2)
            noteDao.addNote(note3)
            noteDao.deleteNotes(listOf(1, 3))
            val note_2 =noteDao.getNoteById(2)
            Assertions.assertAll(
                {Assertions.assertEquals(note2, note_2) {"Notes are getting deleted from unspecifed positins."} },
                {Assertions.assertNotEquals(note1, noteDao.getAllNotes().getOrAwaitValue()[0]) {"Note at specified position has not been deleted"} }
            )


        }
}