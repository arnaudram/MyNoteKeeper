package com.example.mynotekeeper.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mynotekeeper.databinding.FragmentCreateNoteBinding
import com.example.mynotekeeper.dataclasses.Note
import com.example.mynotekeeper.room.getSingleDataManager
import com.example.mynotekeeper.viewmodel.CreateNoteViewModel
import com.example.mynotekeeper.viewmodel.CreateNoteViewModelFactory
import com.google.android.material.snackbar.Snackbar

class CreateNoteFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCreateNoteBinding.inflate(inflater)


        val application = requireNotNull(this.activity).application
        val noteDao = getSingleDataManager(application.applicationContext).noteDao

        val createNoteViewModelFactory = CreateNoteViewModelFactory(application, noteDao)
        val createNoteViewModel =
            ViewModelProvider(this, createNoteViewModelFactory).get(CreateNoteViewModel::class.java)

        binding.createNoteViewModel = createNoteViewModel
        binding.lifecycleOwner = this

        createNoteViewModel.onSaveEvent.observe(viewLifecycleOwner, Observer {
            if(it){
                var course = binding.editNotCourse.text.toString()
                var title = binding.editNoteTitle.text.toString()
                var text = binding.editNoteText.text.toString()


                if (noEmptyField(course,title,text) ){
                    val note= Note(note_course =  course,note_title =  title, note_text = text)

                    createNoteViewModel.insert(note)
                    createNoteViewModel.doneSave()
                    Toast.makeText(context,"Saved",Toast.LENGTH_LONG).show()
                    cleanField(binding)

                }
                else{
                    view?.let { it1 -> Snackbar.make(it1,"All field are required",Snackbar.LENGTH_LONG).show() }
                }
            }

        })
        createNoteViewModel.navigateBackToList.observe(viewLifecycleOwner, Observer {
             if (it){
                 this.findNavController().navigate(CreateNoteFragmentDirections.actionCreateNoteFragmentToListNotesFragment())
             }
        })

        return binding.root
    }

    private fun cleanField(binding: FragmentCreateNoteBinding) {
        binding.editNotCourse.text.clear()
        binding.editNoteTitle.text.clear()
        binding.editNoteText.text.clear()
    }


    private fun noEmptyField(course: String, title: String, text: String): Boolean {
      return course.isNotEmpty()&& title.isNotEmpty()&& text.isNotEmpty()
    }
}