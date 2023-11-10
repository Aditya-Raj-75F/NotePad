package com.example.notepad.ui

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.notepad.NotepadApplication
import com.example.notepad.db.NoteModel
import com.example.notepad.ui.fragments.NoteEditableFragmentDirections
import com.example.notepad.util.Coroutines
import com.example.notepad.util.NavigationHelper
import com.example.notepad.util.confirmDeleteDialog

open class NoteViewModel(appContext: Context) : ViewModel() {
    val context = appContext.applicationContext as NotepadApplication
    open var noteRepository= context.appContainer.noteRepository

    var id: Int = -1
    var title: String? = null
    var content: String? = null


    open fun onSaveNote(view: View) {
        Coroutines.main {
            if(id == -1) {
                noteRepository.addNote(title, content)
            } else {
                noteRepository.updateNote(id, title, content)
            }
        }
        NavigationHelper.switchFragment(view, NoteEditableFragmentDirections.editToViewNote())
    }

    open fun onDeleteNote(view: View, noteId: Int){
        confirmDeleteDialog(view) {
            Coroutines.main {
                noteRepository.deleteNote(noteId)
            }
        }
    }

    open fun onDeleteNote(view: View){
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

    open fun deleteSelectedNotes(view: View, noteIds: List<Int>){
        confirmDeleteDialog(view) {
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