package com.example.mynotekeeper


import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.mynotekeeper.dataclasses.Note
@BindingAdapter("bind_course")
fun TextView.noteCourse(note: Note){
    this.text=note.note_course

}
@BindingAdapter("bind_title")
fun TextView.noteTitle(note: Note){
  this.text=note.note_title
}