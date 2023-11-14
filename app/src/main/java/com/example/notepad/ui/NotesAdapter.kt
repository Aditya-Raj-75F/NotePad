package com.example.notepad.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.R
import com.example.notepad.db.NoteModel
import com.example.notepad.ui.fragments.NoteListFragmentDirections
import com.example.notepad.util.confirmDeleteDialog

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(){
    private var notes: MutableList<NoteModel> = mutableListOf()
    private var viewModel: NoteViewModel? = null
    private val selectedNotes = mutableSetOf<Int>()
    val listEmptyOrNot = MutableLiveData<Boolean>()
    inner class NoteViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val titleNotes: TextView = view.findViewById(R.id.noteTitleItem)
        val contentNotes: TextView = view.findViewById(R.id.noteContentItem)
        val idNotes: TextView = view.findViewById(R.id.idTextView)
    }

    fun setParameters(noteList: List<NoteModel>, viewModelArgs: NoteViewModel) {
        notes = noteList.toMutableList()
        viewModel = viewModelArgs
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
        holder.view.setOnClickListener {
            if(selectedNotes.size>0)
                toggleSelection(it, latestPosition)
        }
        holder.view.setOnLongClickListener {
                toggleSelection(it, latestPosition)
            true
        }
        selectedNotes.takeIf { it.isNotEmpty() }.run {
            editButton.setOnClickListener {
                    val action = NoteListFragmentDirections.viewToEditNote()
                    action.noteArgs = note
                    Navigation.findNavController(it).navigate(action)
            }
            deleteButton.setOnClickListener {
                confirmDeleteDialog(it) { viewModel?.onDeleteNote(note.id) }
            }
        }

    }

    private fun toggleSelection(view: View, position: Int) {
        val beforeSelection = selectedNotes.isEmpty()
        if(selectedNotes.contains(position)) {
            view.setBackgroundResource(R.color.white)
            selectedNotes.remove(position)
        } else {
            view.setBackgroundResource(R.color.faded_yellow)
            selectedNotes.add(position)
        }
        val afterSelection = selectedNotes.isEmpty()
        Log.d("USER_TEST", "before selection list empty = $beforeSelection")
        Log.d("USER_TEST", "after selection list empty = $afterSelection")
        Log.d("USER_TEST", "0 to 1 or 1 to 0 = ${beforeSelection xor afterSelection}")
        if(beforeSelection xor afterSelection) {
            Log.d("USER_TEST","Updating Live Data")
            listEmptyOrNot.value = afterSelection
        }
    }
    fun clearSelection() {
        selectedNotes.clear()
        listEmptyOrNot.value = true
    }

    // Get a list of selected items
    fun getSelectedItems(): List<Int> {
        return selectedNotes.toList().mapNotNull { notes.getOrNull(it)?.id } // return type = List<NoteModel>
    }

}