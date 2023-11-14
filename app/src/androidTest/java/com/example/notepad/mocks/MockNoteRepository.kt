package com.example.notepad.mocks

import com.example.notepad.db.NoteDao
import com.example.notepad.repository.NoteRepository
import org.mockito.Mockito.mock

class MockNoteRepository : NoteRepository(mock(NoteDao::class.java)){
}