package com.example.notepad.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.notepad.NotepadApplication
import com.example.notepad.db.NoteModel
import com.example.notepad.util.Coroutines

open class NoteViewModel(appContext: Context) : ViewModel() {
    val context = appContext.applicationContext as NotepadApplication
    open var noteRepository= context.appContainer.noteRepository

    var id: Int = -1
    var title: String? = null
    var content: String? = null
    var color: Int? = null

    open fun onSaveNote() {
        Coroutines.main {
            if(id == -1) {
                noteRepository.addNote(title, content, color)
            } else {
                noteRepository.updateNote(id, title, content, color)
            }
        }
    }

    open fun onDeleteNote(noteId: Int){
            Coroutines.main {
                noteRepository.deleteNote(noteId)
            }
    }

    open fun onDeleteNote(){
            Coroutines.main {
                noteRepository.deleteNote(id)
            }
    }

    fun getAllNotes(): LiveData<List<NoteModel>> {
        return noteRepository.getAllNotes()
    }

    open fun deleteSelectedNotes(noteIds: List<Int>){
            Coroutines.main {
                noteRepository.deleteNotes(noteIds)
            }
    }
    fun loadExistingNoteData(existingNote: NoteModel) {
        id = existingNote.id
        title = existingNote.title
        content = existingNote.content
        color = existingNote.noteColor
    }
}