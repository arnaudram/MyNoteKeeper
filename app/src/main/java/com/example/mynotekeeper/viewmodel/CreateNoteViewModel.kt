package com.example.mynotekeeper.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.mynotekeeper.dataclasses.Note
import com.example.mynotekeeper.room.NoteDao
import kotlinx.coroutines.*

class CreateNoteViewModel(application: Application, private val noteDao: NoteDao) :
    AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _onSaveEvent = MutableLiveData<Boolean>()
    val onSaveEvent: LiveData<Boolean>
        get() = _onSaveEvent

    private var _navigateBackToList = MutableLiveData<Boolean>()
    val navigateBackToList: LiveData<Boolean>
        get() = _navigateBackToList

    init {
        _onSaveEvent.value=false
        _navigateBackToList.value=false
    }

    fun insert(note: Note){
        uiScope.launch {
            withContext(Dispatchers.IO){
                noteDao.insert(note)
            }
        }
    }
  fun onSave(){
      _onSaveEvent.value=true
  }

    fun doneSave(){
        _onSaveEvent.value=false
        _navigateBackToList.value=true
    }
    override fun onCleared() {
        super.onCleared()
        uiScope.cancel()
    }
}

class CreateNoteViewModelFactory(val application: Application, val noteDao: NoteDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateNoteViewModel::class.java)) {
            return CreateNoteViewModel(application, noteDao) as T
        } else throw IllegalArgumentException("Wrong viewModel class")
    }

}