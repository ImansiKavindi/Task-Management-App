package com.example.ticked

import NotesManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(
    private var notes: MutableList<Note>, // Change to MutableList
    private val context: Context,
    private val notesManager: NotesManager // Pass the NotesManager instance
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextViev) // Ensure correct spelling
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
        val layout: LinearLayout = itemView.findViewById(R.id.mylayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]

        // Set background color based on priority
        holder.layout.setBackgroundColor(when (note.priority) {
            "High" -> Color.parseColor("#FFA07A")
            "Medium" -> Color.parseColor("#FFDAB9")
            else -> Color.parseColor("#ADD8E6") // Low or default
        })

        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content

        holder.updateButton.setOnClickListener {
            val intent = Intent(context, UpdateNoteActivity::class.java).apply {
                putExtra("note_id", note.id) // Pass the note ID
            }
            (context as AppCompatActivity).startActivityForResult(intent, Tasklist.UPDATE_NOTE_REQUEST) // Start activity for result
        }

        holder.deleteButton.setOnClickListener {
            val positionToDelete = holder.adapterPosition
            if (positionToDelete != RecyclerView.NO_POSITION) {
                notesManager.deleteNote(note.id) // Delete the note from the data source
                notes.removeAt(positionToDelete) // Remove the note from the list
                notifyItemRemoved(positionToDelete) // Notify the adapter of the removed item
                Toast.makeText(context, "Note Deleted!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun updateNote(updatedNote: Note) {
        val index = notes.indexOfFirst { it.id == updatedNote.id }
        if (index != -1) {
            notes[index] = updatedNote // Update the note in the list
            notifyItemChanged(index) // Notify that a specific item has changed
        }
    }

    fun refreshData(newNotes: List<Note>) {
        notes.clear() // Clear the current list
        notes.addAll(newNotes) // Add the new notes
        notifyDataSetChanged() // Notify the adapter to refresh the view
    }
}
