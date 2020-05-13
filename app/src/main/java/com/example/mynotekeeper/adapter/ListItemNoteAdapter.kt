package com.example.mynotekeeper.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotekeeper.databinding.ItemListBinding
import com.example.mynotekeeper.dataclasses.Note

class ListItemNoteAdapter(var itemListNoteListener: ItemListNoteListener):ListAdapter<Note,ListItemNoteAdapter.ListItemViewHolder>(NoteDiffutilCallBack) {

    class ListItemViewHolder(private val binding: ItemListBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(
            itemNote: Note?,
            itemListNoteListener: ItemListNoteListener

        ) {
            itemNote?.let {
                binding.itemNote=itemNote
                binding.itemNoteListener=itemListNoteListener
                binding.executePendingBindings()
            }
        }

    }

    object NoteDiffutilCallBack: DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
          return oldItem===newItem
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
          return oldItem.note_id==newItem.note_id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val binding=ItemListBinding.inflate(LayoutInflater.from(parent.context))
        return ListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
         val itemNote=getItem(position)
        holder.bind(itemNote,itemListNoteListener)
    }

}

class ItemListNoteListener(var itemNote: (Note) -> Unit) {
    fun onClick(itemClick: Note?) {
    itemClick?.let {
        return itemNote.invoke(it)
    }
    }
}