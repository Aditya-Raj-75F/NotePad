package com.example.notepad.ui

import android.view.View
import com.example.notepad.db.NoteModel

interface NoteListener {
    fun onStarted(allNotes: List<NoteModel>)
    fun onSuccess(view: View)
    fun onFailure()
}