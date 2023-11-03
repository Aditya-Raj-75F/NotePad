package com.example.notepad.util

import android.view.View
import androidx.appcompat.app.AlertDialog

fun confirmDeleteDialog(view: View, work: ()->Unit) {
    val alertBuilder = AlertDialog.Builder(view.context)
    alertBuilder.setTitle("Delete Confirmation")
    alertBuilder.setMessage("Are you sure you want to continue?")
    alertBuilder.setPositiveButton("Yes") { _, _ ->
        work()
    }
    alertBuilder.setNegativeButton("No") { _, _ ->
    }
    alertBuilder.create().show()
}