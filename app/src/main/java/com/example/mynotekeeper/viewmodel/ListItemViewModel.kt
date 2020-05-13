package com.example.mynotekeeper.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mynotekeeper.dataclasses.Note
import com.example.mynotekeeper.room.DataManager
import com.example.mynotekeeper.room.NoteDao
import kotlinx.coroutines.*

class ListItemViewModel(application: Application, private val noteDao: NoteDao) :
    AndroidViewModel(application) {


    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val notes = noteDao.getAllNote()
    private var _fabEvent = MutableLiveData<Boolean>()
    val fabEvent: LiveData<Boolean>
        get() = _fabEvent

private var _itemSelectedEvent=MutableLiveData<Note>()
           val itemSelectedEvent: MutableLiveData<Note>
       get() = _itemSelectedEvent


    init {
        _fabEvent.value = false


    }

fun onItemSelected(note:Note){
    _itemSelectedEvent.value=note

}
fun doneSelectItem(){
    _itemSelectedEvent.value=null
}

    fun onFabListener() {
        _fabEvent.value = true
    }

    fun onFinishedFabListener() {
        _fabEvent.value = false
    }

    override fun onCleared() {
        super.onCleared()
        uiScope.cancel()
    }
}