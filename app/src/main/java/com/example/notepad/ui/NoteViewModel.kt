package com.example.notepad.ui

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.notepad.NotepadApplication
import com.example.notepad.db.NoteModel
import com.example.notepad.repository.NoteRepository
import com.example.notepad.ui.fragments.NoteEditableFragmentDirections
import com.example.notepad.util.Coroutines
import com.example.notepad.util.NavigationHelper
import com.example.notepad.util.confirmDeleteDialog

class NoteViewModel(appContext: Context) : ViewModel() {
    private val noteRepository: NoteRepository

    init {
        val context = appContext.applicationContext as NotepadApplication
        noteRepository = context.appContainer.noteRepository
    }
    var id: Int = -1
    var title: String? = null
    var content: String? = null


    fun onSaveNote(view: View) {
        Coroutines.main {
            if(id == -1) {
                noteRepository.addNote(title, content)
            } else {
                noteRepository.updateNote(id, title, content)
            }
        }
        NavigationHelper.switchFragment(view, NoteEditableFragmentDirections.editToViewNote())
    }

    fun onDeleteNote(view: View, noteId: Int) {
        confirmDeleteDialog(view) {
            Coroutines.main {
                noteRepository.deleteNote(noteId)
            }
        }
    }

    fun onDeleteNote(view: View) {
        confirmDeleteDialog(view) {
            Coroutines.main {
                noteRepository.deleteNote(id)
            }
            NavigationHelper.switchFragment(view, NoteEditableFragmentDirections.editToViewNote())
        }

    }

    fun getAllNotes(): LiveData<List<NoteModel>> {
        return noteRepository.getAllNotes()
    }

    fun deleteSelectedNotes(view: View, noteIds: List<Int>) {
        confirmDeleteDialog(view) {
            Log.d("USER_TEST","ids are = $noteIds")
            Coroutines.main {
                noteRepository.deleteNotes(noteIds)
            }
        }
    }
    fun loadExistingNoteData(existingNote: NoteModel) {
        id = existingNote.id
        title = existingNote.title
        content = existingNote.content
    }
}