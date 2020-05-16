package com.example.mynotekeeper

import android.content.Context
import android.provider.ContactsContract
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.mynotekeeper.adapter.ListItemNoteAdapter
import com.example.mynotekeeper.dataclasses.Note
import com.example.mynotekeeper.room.DataManager
import com.example.mynotekeeper.room.NoteDao
import com.example.mynotekeeper.room.getSingleDataManager

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class NextThroughNotesTest{
   lateinit var activity: MainActivity
    lateinit var singleDataManager: DataManager
    lateinit var noteDao: NoteDao
   @get:Rule
   val mainActivityRule=ActivityTestRule(MainActivity::class.java)
@Before
fun setUp(){
    activity=mainActivityRule.activity
    val context= activity.applicationContext
    singleDataManager= getSingleDataManager(context)
    noteDao=singleDataManager.noteDao

}
    @Test
    fun nextThroughNotes(){

        onView(withId(R.id.navigation_drawer)).perform(DrawerActions.open())
        onView(withId(R.id.navigation_view)).perform(NavigationViewActions.navigateTo(R.id.listNotesFragment))

        onView(withId(R.id.recycle_view)).perform(RecyclerViewActions.actionOnItemAtPosition<ListItemNoteAdapter.ListItemViewHolder>(0, click()))

        val firstItem=noteDao.getFirstItem()


       onView(withId(R.id.detail_course)).check(matches(withText(firstItem?.note_course)))
        onView(withId(R.id.detail_title)).check(matches(withText(firstItem.note_title)))
        onView(withId(R.id.detail_text)).check(matches(withText(firstItem.note_text)))
    }
}