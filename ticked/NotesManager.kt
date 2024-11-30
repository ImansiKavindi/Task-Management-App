import android.content.Context
import android.content.SharedPreferences
import com.example.ticked.Note
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

class NotesManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("NotesPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    object NoteIdGenerator {
        private var currentId = 0

        fun getNextId(): Int {
            return currentId++
        }
    }

    // Insert a new note
    fun insertNote(note: Note) {
        val notes = getAllNotes().toMutableList()
        // Create a new note with a unique ID
        val newNote = note.copy(id = NoteIdGenerator.getNextId())
        notes.add(newNote)
        saveNotes(notes)
    }


    // Retrieve all notes
    fun getAllNotes(): List<Note> {
        val json = sharedPreferences.getString("notes", null)
        return if (json != null) {
            val type = object : TypeToken<List<Note>>() {}.type
            gson.fromJson(json, type) ?: emptyList()  // Use safe call to avoid null pointer
        } else {
            emptyList()
        }
    }

    // Update an existing note
    fun updateNote(updatedNote: Note): Boolean {
        val notes = getAllNotes().map { if (it.id == updatedNote.id) updatedNote else it }
        saveNotes(notes)
        return true
    }

    // Get a note by its ID
    fun getNoteByID(noteId: Int): Note? {
        return getAllNotes().find { it.id == noteId }
    }

    // Delete a note by its ID
    fun deleteNote(noteId: Int) {
        val notes = getAllNotes().filter { it.id != noteId }
        saveNotes(notes)
    }

    // Save notes to SharedPreferences
    fun saveNotes(notes: List<Note>) {
        val json = gson.toJson(notes)
        sharedPreferences.edit().putString("notes", json).apply()
    }
}
