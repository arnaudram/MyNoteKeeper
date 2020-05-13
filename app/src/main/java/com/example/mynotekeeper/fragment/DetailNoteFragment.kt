package com.example.mynotekeeper.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        return binding.root
    }
}