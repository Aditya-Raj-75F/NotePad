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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val NOTE_ID_ARG : String = "noteId"
private const val NOTE_ARG : String = "noteArg"
class NoteEditableFragment : BaseFragment(), NoteListener {

    private var noteId : Int? = null
    private var note : NoteModel? = null
    lateinit var binding: FragmentNoteEditableBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            noteId = it.getInt(NOTE_ID_ARG)
            note = NoteEditableFragmentArgs.fromBundle(it).noteArgs
            if (noteId!=-1) NoteViewModel().configureId(noteId!!)
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
        binding = DataBindingUtil.inflate< FragmentNoteEditableBinding>(inflater,R.layout.fragment_note_editable, container, false)
        binding.viewmodel = viewModel
        viewModel.noteListener = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NoteEditableFragment().apply {
                arguments = Bundle().apply {
                    putString(NOTE_ID_ARG, param1)
                    putString(NOTE_ARG, param2)
                }
            }
    }

    override fun onStarted(allNotes: List<NoteModel>) {
        TODO("Not yet implemented")
    }

    override fun onSuccess(view: View) {
        val action = NoteEditableFragmentDirections.actionNoteEditableFragmentToNoteListFragment()
        Navigation.findNavController(view).navigate(action)
    }

    override fun onFailure() {
        TODO("Not yet implemented")
    }
}