package com.example.mynotekeeper.fragment

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mynotekeeper.R
import com.example.mynotekeeper.databinding.FragmentDetailNoteBinding
import com.example.mynotekeeper.dataclasses.Note

class DetailNoteFragment:Fragment() {
    lateinit var receidNote:Note
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding=FragmentDetailNoteBinding.inflate(inflater)
          receidNote=DetailNoteFragmentArgs.fromBundle(requireArguments()).itemNote
        binding.noteSelected=receidNote

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_share_note,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.share_note-> {
                shareNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun shareNote() {
     // Toast.makeText(context,"Share With",Toast.LENGTH_LONG).show()
        val subject="${receidNote.note_title}"
        val text="hello sir, this is what i have learnt so for. ${receidNote.note_course} \n ${receidNote.note_title} \n ${receidNote.note_text}"
        val shareNoteIntent=Intent(Intent.ACTION_SEND)
            .setType("message/rfc2822")
            .putExtra("subject",subject)
            .putExtra("text",text)

      val chooser=Intent.createChooser(shareNoteIntent,"Share With")
       // startActivity(shareNoteIntent)
        if (shareNoteIntent.resolveActivity(requireActivity().packageManager)!=null){
              startActivity(shareNoteIntent)
        }
    }
}