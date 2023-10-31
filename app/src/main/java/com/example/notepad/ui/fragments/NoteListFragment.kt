package com.example.notepad.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.R
import com.example.notepad.databinding.FragmentNoteListBinding
import com.example.notepad.db.NoteModel
import com.example.notepad.ui.NoteListener
import com.example.notepad.ui.NoteViewModel
import com.example.notepad.ui.NotesAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
class NoteListFragment : BaseFragment() {
    lateinit var viewModel: NoteViewModel
    lateinit var binding: FragmentNoteListBinding
//    lateinit var recyclerView: RecyclerView

    // Called between onCreate and onViewCreated for fragments to instantiate their UI. Inflation occurs here.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // returns the view model for this fragment.
        viewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        // inflating layouts supporting both view binding and data binding
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_note_list, container, false)
        // connecting viewmodel with the binding of the fragment
        binding.viewmodel = viewModel
        // returns the outer most view associated with the binding
        return binding.root
    }

    // called just after onCreateView but before any savedInstanceState has been restored to the view.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // noteList refers to RecyclerView and its layoutManager is set to LinearLayout for holding itemViews
        binding.noteList.layoutManager = LinearLayoutManager(view.context)

        viewModel.getAllNotes(view).observe(viewLifecycleOwner) { allNotes ->
            binding.noteList.adapter = NotesAdapter(allNotes)
        }
        binding.addNoteButton.setOnClickListener {
            val action = NoteListFragmentDirections.viewToEditNote()
            Navigation.findNavController(it).navigate(action)
        }
    }
}