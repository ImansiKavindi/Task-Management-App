package com.example.ticked

import NotesManager
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ticked.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var notesManager: NotesManager // Use NotesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize the Spinner
        val spinner = binding.prioritySpinner

        // Populate Spinner with priority options
        val priorities = arrayOf("High", "Medium", "Low")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, priorities)
        spinner.adapter = adapter

        // Initialize NotesManager
        notesManager = NotesManager(this)

        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val priority = binding.prioritySpinner.selectedItem.toString()
            val note = Note(0, title, content, priority)

            // Save the note using NotesManager
            // Use the NotesManager to save the note
            notesManager.insertNote(note) // Call insertNote instead of saveNotes
            finish() // Close the activity
            Toast.makeText(this, "Note Saved!", Toast.LENGTH_SHORT).show()
        }
    }
}
