package com.example.notepad.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.R
import com.example.notepad.databinding.NoteItemBinding
import com.example.notepad.db.NoteModel
import com.example.notepad.ui.fragments.NoteListFragmentDirections

class NotesAdapter(private var notes: List<NoteModel>, private val viewModel: NoteViewModel) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(){
    inner class NoteViewHolder(val view: View, private val binding: NoteItemBinding) : RecyclerView.ViewHolder(view) {
        val titleNotes: TextView = view.findViewById(R.id.noteTitleItem)
        val contentNotes: TextView = view.findViewById(R.id.noteContentItem)
        val idNotes: TextView = view.findViewById(R.id.idTextView)
        fun bind() {
            binding.viewmodel = viewModel
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val noteItemBinding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false), noteItemBinding
        )}

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.titleNotes.text = notes[position].title
        holder.contentNotes.text = notes[position].content
        holder.idNotes.text = notes[position].id.toString()
        holder.bind()
        val editButton = holder.view.findViewById<ImageView>(R.id.editNoteButton)
        val deleteButton = holder.view.findViewById<ImageView>(R.id.deleteItemButton)
        editButton.setOnClickListener {
            val action = NoteListFragmentDirections.viewToEditNote()
            action.noteArgs = notes[position]
            Navigation.findNavController(it).navigate(action)
        }
        deleteButton.setOnClickListener {
            NoteViewModel().onDeleteNote(it)
        }
    }
}