package com.example.ticked

import NotesManager
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.ticked.databinding.ActivityUpdateNoteBinding

class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var notesManager: NotesManager
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)


        // Initialize NotesManager
        notesManager = NotesManager(this)

        // Initialize the Spinner
        val spinner = binding.editprioritySpinner

        // Populate Spinner with priority options
        val priorities = arrayOf("High", "Medium", "Low")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, priorities)
        spinner.adapter = adapter

        // Get noteId from intent
        noteId = intent.getIntExtra("note_id", -1)
        if (noteId == -1) {
            finish()
            return
        }

        // Retrieve the note by ID
        val note = notesManager.getNoteByID(noteId)
        if (note != null) {
            binding.updatetitleEditText.setText(note.title)
            binding.updatecontentEditText.setText(note.content)


            // Set the spinner selection based on the note's priority
            val priorityPosition = priorities.indexOf(note.priority)
            if (priorityPosition >= 0) {
                spinner.setSelection(priorityPosition)
            }
        }else {
            Toast.makeText(this, "Note not found", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.updatesaveButton.setOnClickListener {
            val newTitle = binding.updatetitleEditText.text.toString()
            val newContent = binding.updatecontentEditText.text.toString()
            val newPriority = spinner.selectedItem.toString()

            // Input validation
            if (newTitle.isBlank() || newContent.isBlank()) {
                Toast.makeText(this, "Title and content cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            // Create updated note
            val updatedNote = Note(noteId, newTitle, newContent, newPriority)
            notesManager.updateNote(updatedNote)

            // Pass the updated note back to the previous activity
            val resultIntent = Intent().apply {
                putExtra("updated_note", updatedNote) // Pass the updated note
            }
            setResult(RESULT_OK, resultIntent) // Set the result
            finish() // Finish the activity

            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
        }
    }
}
