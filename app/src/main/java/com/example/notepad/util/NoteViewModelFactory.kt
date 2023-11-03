package com.example.notepad.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notepad.ui.NoteViewModel

@Suppress("UNCHECKED_CAST")
class NoteViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            return  NoteViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}