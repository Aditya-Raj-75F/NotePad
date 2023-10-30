package com.example.notepad.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.notepad.R
import com.example.notepad.databinding.FragmentNoteEditableBinding
import com.example.notepad.db.NoteModel
import com.example.notepad.ui.NoteListener
import com.example.notepad.ui.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NoteEditableFragment : BaseFragment(), NoteListener {

    private var note : NoteModel? = null
    lateinit var binding: FragmentNoteEditableBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            note = NoteEditableFragmentArgs.fromBundle(it).noteArgs
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        if(note!=null)
            viewModel.loadExistingNoteData(note!!)
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_note_editable, container, false)
        binding.viewmodel = viewModel
        viewModel.noteListener = this
        return binding.root
    }

    override fun onStarted(allNotes: List<NoteModel>) {
        TODO("Not yet implemented")
    }

    override fun onSuccess(view: View) {
        val action = NoteEditableFragmentDirections.editToViewNote()
        Navigation.findNavController(view).navigate(action)
    }

    override fun onFailure() {
        TODO("Not yet implemented")
    }
}