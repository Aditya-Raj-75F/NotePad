package com.example.notepad

import android.app.Application
import android.content.Context
import com.example.notepad.db.NoteDao
import com.example.notepad.db.NoteDatabase
import com.example.notepad.repository.NoteRepository
import com.example.notepad.ui.NotesAdapter

class AppContainer(context: Context) {
    private val noteDatabase : NoteDatabase = NoteDatabase(context)
    private val noteDao : NoteDao = noteDatabase.getNotesDao()
    val noteRepository : NoteRepository = NoteRepository(noteDao)
    val notesAdapter: NotesAdapter = NotesAdapter()
}

class NotepadApplication : Application() {
    lateinit var appContainer: AppContainer
    lateinit var context: Context

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(applicationContext)
    }
}