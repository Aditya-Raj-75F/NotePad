package com.example.notepad.ui.fragments

import androidx.fragment.app.FragmentFactory

class TestFragmentFactory : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String) =
        when(className) {
            NoteListFragment::class.java.toString() -> {
                NoteListFragment()
            }
            NoteEditableFragment::class.java.toString() -> {
                NoteEditableFragment()
            }
            else -> {
                super.instantiate(classLoader, className)
            }
        }
}