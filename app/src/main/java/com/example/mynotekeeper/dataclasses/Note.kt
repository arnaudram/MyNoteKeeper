package com.example.mynotekeeper.dataclasses

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Note_table")
 data class Note(
    @PrimaryKey(autoGenerate = true)
    val note_id:Long=0,
    var note_title:String,
    var note_text:String,
    var note_course:String

) : Parcelable
