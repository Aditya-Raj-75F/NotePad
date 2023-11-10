package com.example.notepad.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.notepad.db.NoteDao
import com.example.notepad.db.NoteModel

// Interacts with the Room database
class NoteRepository(private val noteDao : NoteDao) {

//    private val noteDao : NoteDao
    private val allNotes : LiveData<List<NoteModel>> = noteDao.getAllNotes()

    suspend fun addNote(title: String?, content: String?) {
        val newNote = NoteModel(title, content)
        noteDao.addNote(newNote)
    }

    fun getAllNotes() : LiveData<List<NoteModel>>{
        return allNotes
    }

    suspend fun updateNote(id: Int, title: String?, content: String?) {
        val updatedNote = NoteModel(title, content)
        updatedNote.id = id
        noteDao.updateNote(updatedNote)
    }

    suspend fun deleteNote(id: Int) {
        Log.d("USER_TEST","Entering Delete method with ID: $id")
        if(id==-1) return
        val deleteNote = noteDao.getNoteById(id)
        Log.d("USER_TEST","Note to be deleted = ${noteDao.getAllNotes().value}")
        noteDao.deleteNote(deleteNote!!)
    }

    suspend fun deleteNotes(noteIds : List<Int>) {
        noteDao.deleteNotes(noteIds)
    }

    suspend fun getNoteById(noteId: Int) : NoteModel? {
        return noteDao.getNoteById(noteId)
    }

    suspend fun deleteAllNotes() {
        return noteDao.deleteAllNotes()
    }
}