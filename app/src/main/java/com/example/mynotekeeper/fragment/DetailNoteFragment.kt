package com.example.mynotekeeper.fragment

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mynotekeeper.R
import com.example.mynotekeeper.databinding.FragmentDetailNoteBinding
import com.example.mynotekeeper.dataclasses.Note
import com.example.mynotekeeper.room.getSingleDataManager
import com.example.mynotekeeper.viewmodel.DetailNoteViewModel
import com.example.mynotekeeper.viewmodel.DetailNoteViewModelFactory
import kotlin.properties.Delegates

class DetailNoteFragment : Fragment() {
    lateinit var detailNoteViewModel: DetailNoteViewModel

var notesSize=0

    lateinit var receidNote: Note
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailNoteBinding.inflate(inflater)
            binding.lifecycleOwner=this
        val application = requireNotNull(this.activity).application
        val noteDao = getSingleDataManager(application.applicationContext).noteDao
        val detailNoteViewModelFactory = DetailNoteViewModelFactory(application, noteDao)

        detailNoteViewModel =
            ViewModelProvider(this, detailNoteViewModelFactory)[DetailNoteViewModel::class.java]

        receidNote = DetailNoteFragmentArgs.fromBundle(requireArguments()).itemNote
        detailNoteViewModel.setCurrentNote(receidNote)

        detailNoteViewModel.currentNote.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.noteSelected=it
            }
        })
      //  binding.noteSelected = receidNote
         detailNoteViewModel.onNextEvent.observe(viewLifecycleOwner, Observer {
              if (it){
                  //goto next item
                      detailNoteViewModel.getNextNote()
                        requireActivity().invalidateOptionsMenu()
                  detailNoteViewModel.doneNextItem()
              }
         })





        detailNoteViewModel.allNote.observe(viewLifecycleOwner, Observer {
            notesSize=it.size
        })
        setHasOptionsMenu(true)



        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
       val nextMenuItem=menu.findItem(R.id.action_next_note)

        detailNoteViewModel.currentNotePosition.observe(viewLifecycleOwner, Observer {
            enableNextAction(nextMenuItem,it,notesSize)

        })

        super.onPrepareOptionsMenu(menu)
    }

    private fun enablePreviousAction(menuItem: MenuItem?, currentNotePosition: Long?) {
        menuItem?.let {
            it.setEnabled(currentNotePosition!!>0)

        }
    }

    private fun enableNextAction(menuItem: MenuItem?, currentNotePosition: Long?,notesSize:Int) {
        menuItem?.let {
            it.setEnabled(currentNotePosition!! <notesSize.minus(1))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_share_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.share_note -> {
                shareNote()
                true
            }
            R.id.action_next_note -> {
                detailNoteViewModel.nextItem()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun shareNote() {
        // Toast.makeText(context,"Share With",Toast.LENGTH_LONG).show()
        val subject = "${receidNote.note_title}"
        val text =
            "hello sir, this is what i have learnt so for. ${receidNote.note_course} \n ${receidNote.note_title} \n ${receidNote.note_text}"
        val shareNoteIntent = Intent(Intent.ACTION_SEND)
            .setType("message/rfc2822")
            .putExtra("subject", subject)
            .putExtra("text", text)

        val chooser = Intent.createChooser(shareNoteIntent, "Share With")
        // startActivity(shareNoteIntent)
        if (shareNoteIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(shareNoteIntent)
        }
    }
}