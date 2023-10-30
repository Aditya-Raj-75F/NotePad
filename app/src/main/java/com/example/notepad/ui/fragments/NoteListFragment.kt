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
class NoteListFragment : BaseFragment(), NoteListener {
    lateinit var viewModel: NoteViewModel
    lateinit var binding: FragmentNoteListBinding
    lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        binding = DataBindingUtil.inflate<FragmentNoteListBinding>(inflater,R.layout.fragment_note_list, container, false)
        binding.viewmodel = viewModel
        viewModel.noteListener = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById<RecyclerView>(R.id.noteList)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        viewModel.getAllNotes(view)
        val addNoteButton = view?.findViewById<FloatingActionButton>(R.id.addNoteButton)
        addNoteButton?.setOnClickListener {
            val action = NoteListFragmentDirections.viewToEditNote()
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun onStarted(allNotes: List<NoteModel>) {
        recyclerView.adapter = NotesAdapter(allNotes, viewModel)
    }

    override fun onSuccess(view: View) {
        Log.d("USER_TEST","Clicked on onSuccess")
        Log.d("USER_TEST","${recyclerView.adapter}")
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onFailure() {
        TODO("Not yet implemented")
    }
}