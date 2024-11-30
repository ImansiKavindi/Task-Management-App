package com.example.ticked

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class dialog_add_note : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tasklist)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)


        // Example method to show the dialog
        showAddNoteDialog()
    }

    private fun showAddNoteDialog() {
        // Inflate the custom dialog layout
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.activity_dialog_add_note, null)

        // Find the EditText by its ID in the inflated view
        val editTextNote: EditText = dialogView.findViewById(R.id.editTextNote)

        // Create and show the AlertDialog
        val dialog = AlertDialog.Builder(this)
            .setTitle("Add Note")
            .setView(dialogView) // Set the custom layout as the content of the dialog
            .setPositiveButton("Add") { _, _ ->
                // Retrieve the note text from the EditText
                val note = editTextNote.text.toString()
                // Handle the note (e.g., save it, display it, etc.)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss() // Close the dialog
            }
            .create()

        dialog.show()
    }
}
