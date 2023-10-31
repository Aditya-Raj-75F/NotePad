package com.example.notepad.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.R
import com.example.notepad.db.NoteModel
import com.example.notepad.ui.fragments.NoteListFragmentDirections

class NotesAdapter(private var notes: List<NoteModel>) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(){
    inner class NoteViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val titleNotes: TextView = view.findViewById(R.id.noteTitleItem)
        val contentNotes: TextView = view.findViewById(R.id.noteContentItem)
        val idNotes: TextView = view.findViewById(R.id.idTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        )}

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val latestPosition = itemCount - 1 - position
        val note = notes[latestPosition]
        holder.titleNotes.text = note.title
        holder.contentNotes.text = note.content
        holder.idNotes.text = note.id.toString()
        val editButton = holder.view.findViewById<ImageView>(R.id.editNoteButton)
        val deleteButton = holder.view.findViewById<ImageView>(R.id.deleteItemButton)
        editButton.setOnClickListener {
            val action = NoteListFragmentDirections.viewToEditNote()
            action.noteArgs = note
            Navigation.findNavController(it).navigate(action)
        }
        deleteButton.setOnClickListener {
            NoteViewModel().onDeleteNote(it, note.id)
        }
    }
}