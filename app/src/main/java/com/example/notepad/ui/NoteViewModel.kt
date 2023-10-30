package com.example.notepad.ui

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.notepad.db.NoteModel
import com.example.notepad.repository.NoteRepository
import com.example.notepad.util.Coroutines

class NoteViewModel : ViewModel() {
    var id: Int = -1
    var title: String? = null
    var content: String? = null
    var idString: String? = null

    var noteListener: NoteListener? = null

    fun onSaveNote(view: View) {
        Log.d("USER_TEST","\nTesting for Results\n")
        Toast.makeText(view.context,"Hello There", Toast.LENGTH_LONG).show()
        Coroutines.main {
            if(id == -1) {
                Log.d("USER_TEST","\nAdding new note\n")
                Toast.makeText(view.context,"Adding new Note", Toast.LENGTH_LONG).show()
                NoteRepository().addNote(view.context, title, content)
                noteListener?.onSuccess(view)
            } else {
                Log.d("USER_TEST","\nUpdating note: $content\n")
                NoteRepository().updateNote(view.context, id, title, content)
                noteListener?.onSuccess(view)
            }
        }
    }

    fun onDeleteNote(view: View) {
        Coroutines.main {
            NoteRepository().deleteNote(view.context, id, title, content)
        }
        noteListener?.onSuccess(view)
    }

    fun getAllNotes(view: View) {
//                LiveData<List<NoteModel>> = liveData {
//            val result = NoteRepository().getAllNotes(view.context)
//            emit(result)
//        }
                Coroutines.main {
            noteListener?.onStarted(NoteRepository().getAllNotes(view.context))
//            Log.d("USER_TEST","Inside coroutin in viewMode ${noteList}")
        }
    }

    fun configureId(noteId: Int) {
        id = noteId
    }

    fun configureId(idString: String) {
        id = idString.toInt()
    }

    fun loadExistingNoteData(existingNote: NoteModel) {
        id = existingNote.id
        title = existingNote.title
        content = existingNote.content
    }

    fun deleteNote(view: View, id: Int) {
        Coroutines.main {
            NoteRepository().deleteNote(view.context, id, "", "")
            Log.d("USER_TEST","Deleted the Note")
            Log.d("USER_TEST","$noteListener")
            Toast.makeText(view.context, "Note deleted", Toast.LENGTH_LONG).show()
            noteListener?.onSuccess(view)
        }
    }
}