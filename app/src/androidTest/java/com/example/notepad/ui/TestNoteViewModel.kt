package com.example.notepad.ui

import android.content.Context
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

    override fun onSaveNote() {
        EspressoIdlingResource.increment()
        super.onSaveNote()
        EspressoIdlingResource.decrement()
    }

    override fun onDeleteNote(noteId: Int) {
        EspressoIdlingResource.increment()
        super.onDeleteNote(noteId)
        EspressoIdlingResource.decrement()
    }

    override fun onDeleteNote() {
        EspressoIdlingResource.increment()
        super.onDeleteNote()
        EspressoIdlingResource.decrement()
    }

    override fun deleteSelectedNotes(noteIds: List<Int>) {
        EspressoIdlingResource.increment()
        super.deleteSelectedNotes(noteIds)
        EspressoIdlingResource.decrement()
    }
}