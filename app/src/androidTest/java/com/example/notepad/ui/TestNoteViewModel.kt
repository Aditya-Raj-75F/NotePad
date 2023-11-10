package com.example.notepad.ui

import android.content.Context
import android.view.View
import com.example.notepad.EspressoIdlingResource
import com.example.notepad.repository.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TestNoteViewModel(test_noteRepository: NoteRepository, appContext: Context) : NoteViewModel(appContext) {
    override var noteRepository: NoteRepository
        get() = super.noteRepository
        set(value) {}
    init {

        noteRepository = test_noteRepository
        EspressoIdlingResource.increment()
        CoroutineScope(Dispatchers.IO).launch {
            noteRepository.deleteAllNotes()
            noteRepository.addNote("Hello", "Bye")
            noteRepository.addNote("Aditya", "Raj")
        }
        EspressoIdlingResource.decrement()
    }

    override fun onSaveNote(view: View) {
        EspressoIdlingResource.increment()
        super.onSaveNote(view)
        EspressoIdlingResource.decrement()
    }

    override fun onDeleteNote(view: View, noteId: Int) {
        EspressoIdlingResource.increment()
        super.onDeleteNote(view, noteId)
        EspressoIdlingResource.decrement()
    }

    override fun onDeleteNote(view: View) {
        EspressoIdlingResource.increment()
        super.onDeleteNote(view)
        EspressoIdlingResource.decrement()
    }

    override fun deleteSelectedNotes(view: View, noteIds: List<Int>) {
        EspressoIdlingResource.increment()
        super.deleteSelectedNotes(view, noteIds)
        EspressoIdlingResource.decrement()
    }
}