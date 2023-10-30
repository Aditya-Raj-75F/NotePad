package com.example.notepad.repository

import android.content.Context
import android.util.Log
import com.example.notepad.db.NoteDatabase
import com.example.notepad.db.NoteModel

// Interacts with the Room database
class NoteRepository {
    suspend fun addNote(context: Context, title: String?, content: String?) {
        val newNote = NoteModel(title, content)
        NoteDatabase(context).getNotesDao().addNote(newNote)
    }

    suspend fun getAllNotes(context: Context) : List<NoteModel> {
        return NoteDatabase(context).getNotesDao().getAllNotes()
    }

    suspend fun updateNote(context: Context, id: Int, title: String?, content: String?) {
        val updatedNote = NoteModel(title, content)
        updatedNote.id = id
        NoteDatabase(context).getNotesDao().updateNote(updatedNote)
    }

    suspend fun deleteNote(context: Context, id: Int) {
        Log.d("USER_TEST","Entering Delete method with ID: $id")
        if(id==-1) return
        val deleteNote = NoteDatabase(context).getNotesDao().getNoteById(id)
        NoteDatabase(context).getNotesDao().deleteNote(deleteNote!!)
    }
}