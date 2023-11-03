package com.example.notepad.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notepad.NotepadApplication
import com.example.notepad.R
import com.example.notepad.databinding.FragmentNoteListBinding
import com.example.notepad.ui.NoteViewModel
import com.example.notepad.ui.NotesAdapter
import com.example.notepad.util.NoteViewModelFactory

class NoteListFragment : BaseFragment(){
    private lateinit var binding: FragmentNoteListBinding
    private val viewModel: NoteViewModel by viewModels { NoteViewModelFactory(requireContext()) }
    private lateinit var adapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // returns the view model for this fragment.
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
        adapter= (requireContext().applicationContext as NotepadApplication).appContainer.notesAdapter
        // noteList refers to RecyclerView and its layoutManager is set to LinearLayout for holding itemViews
        binding.noteList.layoutManager = LinearLayoutManager(view.context)

        viewModel.getAllNotes().observe(viewLifecycleOwner) { allNotes ->
            adapter.setParameters(allNotes, viewModel)
            binding.noteList.adapter = adapter
            val adapt = binding.noteList.adapter as NotesAdapter
            Log.d("USER_TEST","Entering observer for main view model")
            adapt.listEmptyOrNot.observe(viewLifecycleOwner) { presence ->
                Log.d("USER_TEST", "Observing selection Notes")
                if (presence) {
                    binding.addNoteButton.visibility = View.VISIBLE
                    binding.multiSelectMenuButton.visibility = View.GONE
                    binding.cancelSelectedItems.visibility = View.GONE
                    for (i in 0 until binding.noteList.childCount) {
                        val viewHolder = binding.noteList.findViewHolderForAdapterPosition(i) as NotesAdapter.NoteViewHolder
                        viewHolder.itemView.findViewById<ImageView>(R.id.deleteItemButton).visibility = View.VISIBLE
                        viewHolder.itemView.findViewById<ImageView>(R.id.editNoteButton).visibility = View.VISIBLE
                        viewHolder.itemView.setBackgroundResource(R.color.white)
                    }
                } else {
                    binding.addNoteButton.visibility = View.GONE
                    binding.multiSelectMenuButton.visibility = View.VISIBLE
                    binding.cancelSelectedItems.visibility = View.VISIBLE
                    for (i in 0 until binding.noteList.childCount) {
                        val viewHolder = binding.noteList.findViewHolderForAdapterPosition(i) as NotesAdapter.NoteViewHolder
                        viewHolder.itemView.findViewById<ImageView>(R.id.deleteItemButton).visibility = View.GONE
                        viewHolder.itemView.findViewById<ImageView>(R.id.editNoteButton).visibility = View.GONE
                    }
                }
            }
        }
        binding.addNoteButton.setOnClickListener {
            val action = NoteListFragmentDirections.viewToEditNote()
            Navigation.findNavController(it).navigate(action)
        }
        binding.multiSelectMenuButton.setOnClickListener {
            viewModel.deleteSelectedNotes(view, (binding.noteList.adapter as NotesAdapter).getSelectedItems())
            (binding.noteList.adapter as NotesAdapter).clearSelection()
        }
        binding.cancelSelectedItems.setOnClickListener {
            (binding.noteList.adapter as NotesAdapter).clearSelection()
        }
    }
}