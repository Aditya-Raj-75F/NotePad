package com.example.notepad.ui

import android.os.Build.VERSION_CODES.M
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.room.Query
import com.example.notepad.db.NoteModel
import com.example.notepad.repository.NoteRepository
import com.example.notepad.ui.fragments.NoteEditableFragment
import com.example.notepad.ui.fragments.NoteEditableFragmentDirections
import com.example.notepad.util.Coroutines
import com.example.notepad.util.NavigationHelper
import com.example.notepad.util.confirmDeleteDialog

class NoteViewModel : ViewModel() {
    var id: Int = -1
    var title: String? = null
    var content: String? = null


private val allNotes: MutableLiveData<List<NoteModel>> = MutableLiveData()
    fun onSaveNote(view: View) {
        Log.d("USER_TEST","\nTesting for Results\n")
        Toast.makeText(view.context,"Hello There", Toast.LENGTH_LONG).show()
        Coroutines.main {
            if(id == -1) {
                Log.d("USER_TEST","\nAdding new note\n")
                NoteRepository(view.context).addNote(title, content)
            } else {
                Log.d("USER_TEST","\nUpdating note: $content\n")
                NoteRepository(view.context).updateNote(id, title, content)
            }
        }
        NavigationHelper.switchFragment(view, NoteEditableFragmentDirections.editToViewNote())
    }

    fun onDeleteNote(view: View, noteId: Int) {
        confirmDeleteDialog(view) {
            Coroutines.main {
                NoteRepository(view.context).deleteNote(noteId)
            }
        }
    }

    fun onDeleteNote(view: View) {
        confirmDeleteDialog(view) {
            Coroutines.main {
                NoteRepository(view.context).deleteNote(id)
            }
            NavigationHelper.switchFragment(view, NoteEditableFragmentDirections.editToViewNote())
        }

    }

    fun getAllNotes(view: View): LiveData<List<NoteModel>> {
        return NoteRepository(view.context).getAllNotes()
    }

    fun deleteSelectedNotes(view: View, noteIds: List<Int>) {
        confirmDeleteDialog(view) {
            Log.d("USER_TEST","ids are = $noteIds")
            Coroutines.main {
                NoteRepository(view.context).deleteNotes(noteIds)
            }
        }
    }
    fun loadExistingNoteData(existingNote: NoteModel) {
        id = existingNote.id
        title = existingNote.title
        content = existingNote.content
    }
}