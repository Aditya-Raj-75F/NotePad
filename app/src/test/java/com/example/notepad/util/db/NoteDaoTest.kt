package com.example.notepad.util.db

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.notepad.db.NoteDao
import com.example.notepad.db.NoteDatabase
import com.example.notepad.db.NoteModel
import com.example.notepad.util.InstantExecutorExtension
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExtendWith(InstantExecutorExtension::class)
class NoteDaoTest {

//    lateinit var noteDatabase: NoteDatabase
//    lateinit var noteDao: NoteDao
//    @Mock
//    private lateinit var mockContext: Context
//
//    @BeforeEach
//    fun setup() {
//        MockitoAnnotations.openMocks(this)
//        Mockito.`when`(mockContext.applicationContext).thenReturn(mockContext)
//        noteDatabase = Room.inMemoryDatabaseBuilder(mockContext, NoteDatabase::class.java).allowMainThreadQueries().build()
//        noteDao = noteDatabase.getNotesDao()
//    }
//
//    @AfterEach
//    fun teardown() {
//        noteDatabase.close()
//    }
//
//    @Test
//    fun test_NotesDao() = runTest{
//        val note = NoteModel(title = "Test Note", content = "This is a test note", 0xFFFFFF)
//        noteDao.addNote(note)
//        noteDao.addNote(note)
//        val allNotes = noteDao.getAllNotes().getOrAwaitValue()
//        Assertions.assertEquals(2, allNotes.size) {"The numeber of notes inserted and the size of the notes table should match"}
//        Assertions.assertEquals(note, allNotes[1])  {"The note inserted should remain the same after insertion."}
//
//    }



    private lateinit var notesDao: NoteDao

    private lateinit var mockDatabase: NoteDatabase
    @Mock
    private lateinit var mockContext: Context

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        `when`(mockContext.applicationContext).thenReturn(mockContext)
        mockDatabase = NoteDatabase(mockContext)
        notesDao = mockDatabase.getNotesDao()
    }

    @Test
    fun testGetAllNotes() {
        // Replace Dispatchers.setMain with runBlockingTest
            // Define the behavior of the suspend DAO method
            val expectedNotes = listOf(NoteModel("Hello World", "Nuce to meet you", 0xFFFFFF))
            val liveData = MutableLiveData<List<NoteModel>>().apply {
                value = expectedNotes
            }

            // Mock the DAO method using runBlockingTest
            `when`(notesDao.getAllNotes()).thenReturn(liveData)

            // Call the suspend DAO method
            val result = notesDao.getAllNotes().value

            // Assert the result
            assertNotEquals(expectedNotes, result)
        }
}