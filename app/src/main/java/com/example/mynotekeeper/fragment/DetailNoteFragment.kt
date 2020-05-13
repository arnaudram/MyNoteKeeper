package com.example.mynotekeeper.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mynotekeeper.R
import com.example.mynotekeeper.databinding.FragmentDetailNoteBinding

class DetailNoteFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding=FragmentDetailNoteBinding.inflate(inflater)
         val receidNote=DetailNoteFragmentArgs.fromBundle(requireArguments()).itemNote
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
      Toast.makeText(context,"Share With",Toast.LENGTH_LONG).show()
    }
}