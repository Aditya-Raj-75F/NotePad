package com.example.notepad.util

import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.notepad.ui.NoteViewModel

fun confirmDeleteDialog(view: View, noteId: Int, work: ()->Unit) {
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