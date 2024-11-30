package com.example.ticked

import NotesManager
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ticked.databinding.TasklistBinding

class Tasklist : AppCompatActivity() {

    private lateinit var binding: TasklistBinding
    private lateinit var notesManager: NotesManager
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

        binding = TasklistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Enable edge-to-edge
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            window.insetsController?.hide(android.view.WindowInsets.Type.statusBars())
            window.insetsController?.hide(android.view.WindowInsets.Type.navigationBars())
        } else {
            // Fallback for API levels below 30
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        // Initialize NotesManager
        notesManager = NotesManager(this)

        // Pass the NotesManager to the adapter
        notesAdapter = NotesAdapter(notesManager.getAllNotes().toMutableList(), this, notesManager)


        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notesRecyclerView.adapter = notesAdapter

        // Set click listener for adding a note
        binding.addButton.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }

        // Set click listener for navigating to home page
        binding.imageButton3.setOnClickListener {
            startActivity(Intent(this, homepage::class.java))
        }

        // Set up window insets listener for layout padding
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UPDATE_NOTE_REQUEST && resultCode == RESULT_OK) {
            val updatedNote: Note? = data?.getParcelableExtra("updated_note")
            updatedNote?.let {
                notesAdapter.updateNote(it) // Update the adapter with the new note
            }
        }
    }

    companion object {
        const val UPDATE_NOTE_REQUEST = 1
    }


    override fun onResume() {
        super.onResume()
        notesAdapter.refreshData(notesManager.getAllNotes())
    }
}
