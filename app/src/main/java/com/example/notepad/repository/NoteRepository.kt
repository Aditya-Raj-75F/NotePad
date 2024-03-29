package com.example.notepad.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.notepad.db.NoteDao
import com.example.notepad.db.NoteModel

// Interacts with the Room database
open class NoteRepository(private val noteDao : NoteDao) {

//    private val noteDao : NoteDao
    private val allNotes : LiveData<List<NoteModel>> = noteDao.getAllNotes()

    suspend fun addNote(title: String?, content: String?, color: Int?) {
        val newNote = NoteModel(title, content, color)
        noteDao.addNote(newNote)
    }

    fun getAllNotes() : LiveData<List<NoteModel>>{
        return allNotes ?: noteDao.getAllNotes()
    }

    suspend fun updateNote(id: Int, title: String?, content: String?, color: Int?) {
        val updatedNote = NoteModel(title, content, color)
        updatedNote.id = id
        noteDao.updateNote(updatedNote)
    }

    suspend fun deleteNote(id: Int) {
        Log.d("USER_TEST","Entering Delete method with ID: $id")
        if(id==-1) return
        val deleteNote = noteDao.getNoteById(id)
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