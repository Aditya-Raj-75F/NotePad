package com.example.notepad.mocks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notepad.db.NoteDao
import com.example.notepad.db.NoteModel

class MockNoteDao : NoteDao {
    private val mockNotes = mutableListOf<NoteModel>()
    private var idCount = 1

    private val liveData = MutableLiveData<List<NoteModel>>()

    override suspend fun addNote(note: NoteModel) {
        note.id = idCount++
        mockNotes.add(note)
    }

    override suspend fun updateNote(note: NoteModel) {
        val index = mockNotes.indexOfFirst {it.id == note.id}
        if(index != -1) {
            mockNotes[index] = note
        }
    }

    override suspend fun deleteAllNotes() {
        mockNotes.clear()
    }

    override fun getAllNotes(): LiveData<List<NoteModel>> {
        return liveData
    }

    override suspend fun getNoteById(noteId: Int): NoteModel? {
        return mockNotes.find { it.id == noteId }
    }

    override suspend fun deleteNotes(noteIds: List<Int>) {
        val toBeDeletedNotes = noteIds.mapNotNull { id ->
            mockNotes.find{it.id == id}
        }
        mockNotes.removeAll(toBeDeletedNotes)
    }

    override suspend fun deleteNote(note: NoteModel) {
        mockNotes.remove(note)
    }
}