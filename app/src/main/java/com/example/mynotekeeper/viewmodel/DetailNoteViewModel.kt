package com.example.mynotekeeper.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DetailNoteViewModel:ViewModel() {


    init {

    }


}

class DetailNoteViewModelFactory:ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailNoteViewModel::class.java)){
            return DetailNoteViewModel()as T
        }
        else throw IllegalArgumentException("Wrong viewModel class")
    }
}