package com.example.notepad.util

import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notepad.ui.NoteViewModel

fun confirmDeleteDialog(view: View, work: ()->Unit) {
    val alertBuilder = AlertDialog.Builder(view.context)
    alertBuilder.setTitle("Confirmation")
    alertBuilder.setMessage("Are you sure you want to delete this note?")
    alertBuilder.setPositiveButton("Yes") { _, _ ->
        work()
    }
    alertBuilder.setNegativeButton("No") { _, _ ->
    }
    alertBuilder.create().show()
}

class NoteListViewModel : ViewModel() {
}