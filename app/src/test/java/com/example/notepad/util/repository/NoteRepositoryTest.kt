package com.example.notepad.repository

import androidx.lifecycle.MutableLiveData
import com.example.notepad.db.NoteDao
import com.example.notepad.db.NoteModel
import com.example.notepad.util.InstantExecutorExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension

@DisplayName("Testing Repository Class")
@ExtendWith(MockitoExtension::class, InstantExecutorExtension::class)
class NoteRepositoryTest {

    @Mock
    private lateinit var mockNotesDao: NoteDao

    // Subject under test
    private lateinit var noteRepository: NoteRepository

    private lateinit var title : String
    private lateinit var content  : String

    @BeforeEach
    fun setup() {
        // Initialize mock annotations
        MockitoAnnotations.openMocks(this)
        // Create the repository with the mock DAO
        noteRepository = NoteRepository(mockNotesDao)

        title = "Hello World"
        content = "It's nice to see you all."
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        val db = Room.inMemoryDatabaseBuilder(context, TestNoteDatabase::class.java).allowMainThreadQueries().build()
//        val noteDao = db.getNotesDao()
//        noteRepository = NoteRepository(noteDao)
    }

    @Test
    @DisplayName("Inserting New Note and Fetching that note")
    fun addAndFetchNoteTest() = runTest{
        val noteList = mutableListOf<NoteModel>()
        `when`(mockNotesDao.addNote(NoteModel(title, content, 0xFFFFFF))).then { noteList.add(NoteModel(title, content, 0xFFFFFF)) }
        `when`(mockNotesDao.getAllNotes()).thenReturn(MutableLiveData<List<NoteModel>>().apply { value = noteList })
        noteRepository.addNote(title, content, 0xFFFFFF)
        assertEquals(NoteModel(title, content, 0xFFFFFF), noteRepository.getAllNotes().value?.get(0)) {"Note details are not matching"}
    }

    @Test
    @DisplayName("Inserting a note and then updating it")
    fun addAndUpdateNoteTest() = runTest {
        val noteList = mutableListOf<NoteModel>()
        val note = NoteModel(title, content, 0xFFFFFF)
        val noteUpdated = NoteModel("Hello Aditya", "How are you doing", 0xFFFFFF)
        noteUpdated.id = 1
        `when`(mockNotesDao.addNote(note)).then { noteList.add(NoteModel(note.title, note.content, note.noteColor)) }
        noteRepository.addNote(note.title, note.content, note.noteColor)
        println("ADDITION\nlist = $noteList")
        `when`(mockNotesDao.updateNote(noteUpdated)).then { noteList.set(noteList.indexOf(note), noteUpdated) }
        noteRepository.updateNote(1, noteUpdated.title, noteUpdated.content, noteUpdated.noteColor)
        println("UPDATION\nlist = $noteList")
        `when`(mockNotesDao.getNoteById(noteUpdated.id)).thenReturn(noteList[0])
        assertEquals(noteUpdated, noteRepository.getNoteById(1)) {"Updated values of notes are not being reflected"}
    }

    @Test
    @DisplayName("Finding a note by its ID and then deleteing it")
    fun findByIDAndDeleteNoteTest() = runTest {
        val note = NoteModel(title, content, 0xFFFFFF)
        note.id = 1
        val noteList = mutableListOf(note)
        `when`(mockNotesDao.getNoteById(1)).thenReturn(noteList[0])
        `when`(mockNotesDao.deleteNote(note!!)).then { noteList.remove(note) }
        noteRepository.deleteNote(note!!.id)
        val note1 = noteRepository.getNoteById(1)
        assertEquals(NoteModel(title, content, 0xFFFFFF), note1)
        assertTrue(noteList.isEmpty()) {"The note was note deleted"}
    }

    @Test
    @DisplayName("Deleting multiple notes by their ID")
    fun deleteMultipleNotesTest() = runTest {
        val noteList = mutableListOf<NoteModel>()
        noteList.add(NoteModel(title, content, 0xFFFFFF))
        title = "Hello Aditya"
        content = "How are you doing?"
        noteList.add(NoteModel(title, content, 0xFFFFFF))
        title = "Hello Yusuf"
        content = "Did you arrive early today?"
        noteList.add(NoteModel(title, content, 0xFFFFFF))
        `when`(mockNotesDao.deleteNotes(listOf(1,3))).then {
            noteList.removeAt(2)
            noteList.removeAt(0)
        }
        noteRepository.deleteNotes(listOf(1,3))
        assertEquals(1, noteList.size) {"All specified notes were not deleted"}
        assertTrue(noteList[0]!=null) {"Specified indexed note not deleted"}
    }

    @Test
    fun testGetAllNotes() = runTest {
        // Define your expected data
        val expectedNotes = listOf(
            NoteModel("Note 1", "Description 1", 0xFF0000),
            NoteModel("Note 2", "Description 2", 0x00FF00)
        )

        // Mock the DAO's behavior when getAllNotes is called
        `when`(mockNotesDao.getNoteById(1)).thenReturn(expectedNotes[0])
        `when`(mockNotesDao.getNoteById(2)).thenReturn(expectedNotes[1])

        // Call the repository method
        val note1 = noteRepository.getNoteById(1)
        val note2 = noteRepository.getNoteById(2)

        // Verify that the result matches the expected data
        assertEquals(expectedNotes[0], note1)
        assertEquals(expectedNotes[1], note2)
        assertNotEquals(expectedNotes[1], note1)
    }

    @Test
    fun testGetAllNotesv2() {
        // Define your expected data
        val expectedNotes = listOf(
            NoteModel("Note 1", "Description 1", 0xFF0000),
            NoteModel("Note 2", "Description 2", 0x00FF00)
        )
        val liveData = MutableLiveData<List<NoteModel>>()
        liveData.value = expectedNotes
        println("USER_TEST\nliveData = $liveData ${liveData.value}")
        // Mock the DAO's behavior when getAllNotes is called
        `when`(mockNotesDao.getAllNotes()).thenReturn(liveData)

        // Call the repository method
        val result = noteRepository.getAllNotes().value
        println("USER_TEST\nfetchedLiveData= $result ")

        // Verify that the result matches the expected data
        assertEquals(expectedNotes, result)
    }

    @Test
    fun test_deleteAllNotes() = runTest{
        val noteList = mutableListOf<NoteModel>()
        noteList.add(NoteModel(title, content, 0xFFFFFF))
        title = "Hello Aditya"
        content = "How are you doing?"
        noteList.add(NoteModel(title, content, 0xFFFFFF))
        title = "Hello Yusuf"
        content = "Did you arrive early today?"
        noteList.add(NoteModel(title, content, 0xFFFFFF))
        `when`(mockNotesDao.deleteAllNotes()).then { noteList.removeAll(noteList) }
        noteRepository.deleteAllNotes()
        assertTrue(noteList.isEmpty())
    }

}