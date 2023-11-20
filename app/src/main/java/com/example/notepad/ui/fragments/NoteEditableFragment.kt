package com.example.notepad.ui.fragments

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.notepad.R
import com.example.notepad.databinding.FragmentNoteEditableBinding
import com.example.notepad.db.NoteModel
import com.example.notepad.ui.ColorSpinnerAdapter
import com.example.notepad.ui.NoteViewModel
import com.example.notepad.util.NavigationHelper
import com.example.notepad.util.NoteViewModelFactory
import com.example.notepad.util.confirmDeleteDialog

class NoteEditableFragment : BaseFragment() {

    private var note : NoteModel? = null
    private lateinit var binding: FragmentNoteEditableBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            note = NoteEditableFragmentArgs.fromBundle(it).noteArgs
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel: NoteViewModel by viewModels { NoteViewModelFactory(requireContext()) }


        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_note_editable, container, false)
        binding.viewmodel = viewModel
        if(note!=null) {
            viewModel.loadExistingNoteData(note!!)
//            binding.editTitle.background = createBackgroundShape(note!!.noteColor?:ContextCompat.getColor(requireContext(),R.color.white))
//            binding.editContent.background = createBackgroundShape(note!!.noteColor?:ContextCompat.getColor(requireContext(),R.color.white))
        }
        if(viewModel.id!=-1) {
            binding.addOrUpdateNote.text = getString(R.string.edit_note)
        }

        val spinner = binding.colorSpinner
        val colorList = listOf(
            ContextCompat.getColor(requireContext(), R.color.white),
            ContextCompat.getColor(requireContext(), R.color.faded_red),
            ContextCompat.getColor(requireContext(), R.color.faded_blue),
            ContextCompat.getColor(requireContext(), R.color.faded_green),
            ContextCompat.getColor(requireContext(), R.color.faded_yellow)
        )
        val colorSpinnerAdapter = ColorSpinnerAdapter(requireContext(), colorList)
        spinner.adapter = colorSpinnerAdapter
        spinner.setSelection(colorList.indexOf(note?.noteColor?:colorList[0]))

        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = colorList[position]
                viewModel.color = selectedItem
                binding.editTitle.background = createBackgroundShape(selectedItem)
                binding.editContent.background = createBackgroundShape(selectedItem)
//                binding.editTitle.background =LayerDrawable(arrayOf(ColorDrawable(selectedItem), binding.editTitle.background))
//                binding.editContent.background =LayerDrawable(arrayOf(ColorDrawable(selectedItem), binding.editContent.background))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.saveNoteButton.setOnClickListener {
            viewModel.onSaveNote()
                    NavigationHelper.switchFragment(it, NoteEditableFragmentDirections.editToViewNote())
        }
        binding.deleteButton.setOnClickListener {
            confirmDeleteDialog(it) {
                viewModel.onDeleteNote()
                NavigationHelper.switchFragment(it, NoteEditableFragmentDirections.editToViewNote())
            }
        }
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    fun createBackgroundShape(color: Int): GradientDrawable {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.shape = GradientDrawable.RECTANGLE
        gradientDrawable.setColor(color)
        gradientDrawable.cornerRadius = 8F
        gradientDrawable.setStroke(2, ContextCompat.getColor(requireContext(), R.color.orange))
        return gradientDrawable
    }
}