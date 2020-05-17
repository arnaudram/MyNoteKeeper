package com.example.mynotekeeper.room

import android.content.Context
import android.provider.ContactsContract
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mynotekeeper.dataclasses.Note
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DataManagerTest{
    lateinit var singleDataManager: DataManager
    lateinit var noteDao: NoteDao
    @Before
    fun setUp(){
        val context=ApplicationProvider.getApplicationContext<Context>()
        singleDataManager= Room.inMemoryDatabaseBuilder(context,DataManager::class.java).allowMainThreadQueries().build()
        noteDao=singleDataManager.noteDao
    }
    @After
    @Throws(IOException::class)
    fun closeDatabase(){
        singleDataManager.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeAndRead(){
        val note= Note(note_title = "note tile",note_text = "note text",note_course = "note course")
        noteDao.insert(note)

        val firstNote= noteDao.getFirstItem()

        assertThat(firstNote.note_course,equalTo(note.note_course))
        assertThat(firstNote.note_text, equalTo(note.note_text))
        assertThat(firstNote.note_title, equalTo(note.note_title))

    }
}