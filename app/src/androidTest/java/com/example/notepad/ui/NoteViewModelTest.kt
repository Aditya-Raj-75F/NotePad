package com.example.notepad.ui
import android.content.Context
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.notepad.NotepadApplication
import com.example.notepad.R
import com.example.notepad.db.NoteModel
import com.example.notepad.db.TestNoteDatabase
import com.example.notepad.repository.NoteRepository
import com.example.notepad.ui.testUtils.getOrAwaitValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*


@DisplayName("Testing View Model of the Notepad")
class NoteViewModelTest {
    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var noteViewModel: NoteViewModel

    private lateinit var context: Context
    private lateinit var view: View
    private lateinit var noteRepository: NoteRepository

    @BeforeEach
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        val db = Room.inMemoryDatabaseBuilder(context, TestNoteDatabase::class.java).allowMainThreadQueries().build()
        noteRepository = NoteRepository(db.getNotesDao())
        view = View(context)
        noteViewModel = NoteViewModel(context).apply { this.noteRepository =noteRepository }
    }

    @Test
    @Disabled
    fun getAllNotesTest() {
        assertEquals(listOf<NoteModel>(),noteViewModel.getAllNotes().getOrAwaitValue())
    }

    @Test
    @Disabled
    fun deleteSelectedNotesTest() = runTest{
        noteRepository.addNote("Hello", "Nice to meet you all", 0xFFFFFF)
        noteRepository.addNote("Hi Guys", "What a nice day.", 0xFFFFFF)
        noteRepository.addNote("Bye everybody", "Stay safe", 0xFFFFFF)
        noteViewModel.deleteSelectedNotes(listOf(1,3))
        delay(5000)
        assertEquals(1, noteRepository.getAllNotes().getOrAwaitValue().size) {"All specified notes were not deleted"}
    }

    @Test
    fun loadExistingDataTest() {
        noteViewModel.id = 3
        noteViewModel.title = "Hello Man"
        noteViewModel.content = "Long time no see"
        assertEquals(3, noteViewModel.id) {"id not updated"}
        assertEquals("Hello Man", noteViewModel.title) {"title not updated"}
        assertEquals("Long time no see", noteViewModel.content) {"content not updated"}
    }

    @Test
    fun testApplicationContextCast() {
        // Arrange
        val appContext: Context = noteViewModel.context

        // Act
        val context = appContext.applicationContext as NotepadApplication

        // Assert
        assertEquals("NotePad", context.getString(R.string.app_name), "Notepad")
        // Add more assertions as needed
    }
}