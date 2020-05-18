package com.example.mynotekeeper.room

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mynotekeeper.dataclasses.Note

@Database(entities = [Note::class],version = 1)
 abstract class DataManager:RoomDatabase() {
abstract val noteDao:NoteDao




}
private lateinit var singleDataManager:DataManager
fun getSingleDataManager(context: Context): DataManager{
    if (!::singleDataManager.isInitialized){
        synchronized(DataManager::class.java){
            singleDataManager=Room.databaseBuilder(context.applicationContext,DataManager::class.java,"datamanager")
                .build()

        }
    }
    return  singleDataManager
}
@Dao
interface NoteDao{
    @Insert
    fun insert(note: Note)
    @Query("SELECT * FROM Note_table")
    fun getAllNote():LiveData<List<Note>>
    @Query("SELECT * FROM Note_table LIMIT 1")
    fun getFirstItem():Note
    @Query("SELECT * FROM Note_table WHERE note_id==:noteId   ")
    fun getNoteById(noteId:Long):Note
}