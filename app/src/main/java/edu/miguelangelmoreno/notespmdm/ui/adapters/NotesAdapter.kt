package edu.miguelangelmoreno.notespmdm.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.miguelangelmoreno.notespmdm.R
import edu.miguelangelmoreno.notespmdm.databinding.ItemNoteBinding
import edu.miguelangelmoreno.notespmdm.model.Note

class NotesAdapter (
    val onNoteClick: (idNote: Int) -> Unit,
    val onNoteDeleteClick: (Note, pos: Int) -> Unit
) : ListAdapter<Note, NotesAdapter.NotesViewHolder>(NotesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NotesViewHolder(layoutInflater.inflate(R.layout.item_note, parent, false), onNoteClick, onNoteDeleteClick)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NotesViewHolder(
        view: View,
        val onNoteClick: (idNote: Int) -> Unit,
        val onNoteDeleteClick: (Note, pos: Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val binding = ItemNoteBinding.bind(view)

        fun bind(note: Note) {
            binding.tvTitle.text = note.title
            binding.tvDate.text = note.date

            binding.imDelete.setOnClickListener { onNoteDeleteClick(note, adapterPosition) }
            binding.itemCard.setOnClickListener { onNoteClick(note.idNote) }
        }
    }
}

class NotesDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(
        oldItem: Note,
        newItem: Note
    ): Boolean {
        return oldItem.idNote == newItem.idNote
    }
    override fun areContentsTheSame(
        oldItem: Note,
        newItem: Note
    ): Boolean {
        return oldItem == newItem
    }
}

