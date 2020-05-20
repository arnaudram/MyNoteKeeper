package com.example.mynotekeeper.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.mynotekeeper.dataclasses.Note
import com.example.mynotekeeper.room.NoteDao
import kotlinx.coroutines.*

class DetailNoteViewModel(application: Application, private val noteDao: NoteDao) :
    AndroidViewModel(application) {

 val allNote by lazy {


             noteDao.getAllNote()




}

    private var viewModelJob = Job()
    private var uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _currentNote = MutableLiveData<Note>()
    val currentNote: MutableLiveData<Note>
        get() = _currentNote

    private var _onNextEvent = MutableLiveData<Boolean>()
    val onNextEvent: LiveData<Boolean>
        get() = _onNextEvent

   /* private var _onPreviousEvent = MutableLiveData<Boolean>()
    val onPreviousEvent: LiveData<Boolean>
        get() = _onPreviousEvent*/


    private var _currentNotePosition=MutableLiveData<Long>()
             val currentNotePosition:LiveData<Long>
                   get() = _currentNotePosition
    init {
        _onNextEvent.value = false
       // _onPreviousEvent.value=false
        //_currentNotePosition.value=0

    }

  /*  fun getPreviousNote(){
        uiScope.launch{
            var newNote= withContext(Dispatchers.IO){
                var currentNoteId = _currentNote.value?.note_id
                val newNoteId = currentNoteId!! -1
                return@withContext noteDao.getNoteById(newNoteId!!)
            }
            _currentNote.value = newNote
            _currentNotePosition.value=_currentNotePosition.value?.minus(1)
        }

    }*/


    fun getNextNote() {
        var currentNoteId = _currentNote.value?.note_id
        val newNoteId = currentNoteId?.plus(1)
        uiScope.launch {
            val newNote = withContext(Dispatchers.IO) {


                // val newNote=  noteDao.getNoteById(newNoteId!!)
                //  noteFromDb.value=async { noteDao.getNoteById(newNoteId!!) }
                return@withContext noteDao.getNoteById(newNoteId!!)

            }

            _currentNote.value = newNote
            _currentNotePosition.value=_currentNotePosition.value?.plus(1)

        }


    }


    fun setCurrentNote(note: Note) {
        _currentNote.value = note
        _currentNotePosition.value=note.note_id
    }

    fun nextItem() {
        _onNextEvent.value = true
    }

    fun previousItem() {
        _onNextEvent.value = true
    }

    fun doneNextItem() {
        _onNextEvent.value = false
    }
    fun donePreviousItem() {
        _onNextEvent.value = false
    }


    override fun onCleared() {
        super.onCleared()
        uiScope.cancel()
    }
}

class DetailNoteViewModelFactory(
    private val application: Application,
    private val noteDao: NoteDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailNoteViewModel::class.java)) {
            return DetailNoteViewModel(application, noteDao) as T
        } else throw IllegalArgumentException("Wrong viewModel class")
    }
}