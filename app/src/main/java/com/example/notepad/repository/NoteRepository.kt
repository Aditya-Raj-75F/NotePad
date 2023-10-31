package com.example.notepad.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.notepad.db.NoteDao
import com.example.notepad.db.NoteDatabase
import com.example.notepad.db.NoteModel

// Interacts with the Room database
class NoteRepository(context: Context) {
    private val noteDao : NoteDao
    private val allNotes : LiveData<List<NoteModel>>

    init {
        val database = NoteDatabase(context)
        noteDao = database.getNotesDao()
        allNotes = noteDao.getAllNotes()
    }
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
        noteDao.deleteNote(deleteNote!!)
    }
}