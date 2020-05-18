package com.example.mynotekeeper.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mynotekeeper.room.DataManager
import com.example.mynotekeeper.room.NoteDao

class ListItemViewModelFactory(private val application: Application, private val noteDao: NoteDao):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListItemViewModel::class.java)){
            return ListItemViewModel(application,noteDao) as T
        }
        else throw IllegalArgumentException("Wrong viewModel class")
    }
}