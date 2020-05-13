package com.example.mynotekeeper.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mynotekeeper.adapter.ItemListNoteListener
import com.example.mynotekeeper.adapter.ListItemNoteAdapter
import com.example.mynotekeeper.databinding.FragmentListNotesBinding
import com.example.mynotekeeper.room.DataManager
import com.example.mynotekeeper.room.getSingleDataManager
import com.example.mynotekeeper.viewmodel.ListItemViewModel
import com.example.mynotekeeper.viewmodel.ListItemViewModelFactory

class ListNotesFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding=FragmentListNotesBinding.inflate(inflater)
        val application= requireNotNull(this.activity).application
        val noteDao= getSingleDataManager(application.applicationContext).noteDao



        val listItemViewModelFactory=ListItemViewModelFactory(application,noteDao)
        val listItemViewModel=ViewModelProvider(this,listItemViewModelFactory).get(ListItemViewModel::class.java)

        binding.listItemViewModel=listItemViewModel
        binding.lifecycleOwner = this

        val adapter=ListItemNoteAdapter(ItemListNoteListener {
            listItemViewModel.onItemSelected(it)
        })
        binding.recycleView.adapter=adapter

        listItemViewModel.notes.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        listItemViewModel.itemSelectedEvent.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(ListNotesFragmentDirections.actionListNotesFragmentToDetailNoteFragment(it))
                listItemViewModel.doneSelectItem()
            }

        })

        listItemViewModel.fabEvent.observe(viewLifecycleOwner, Observer {
            if (it){
                this.findNavController().navigate(ListNotesFragmentDirections.actionListNotesFragmentToCreateNoteFragment())
                listItemViewModel.onFinishedFabListener()
            }
        })
        return binding.root
    }

}