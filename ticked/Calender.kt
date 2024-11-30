package com.example.ticked

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ticked.adapter.TaskAdapter
import com.example.ticked.data.TaskManager
import com.example.ticked.model.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class Calender : AppCompatActivity() {

    private lateinit var calendarView: android.widget.CalendarView // Use standard CalendarView
    private lateinit var selectedDateTextView: TextView
    private lateinit var addTaskButton: Button
    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskManager: TaskManager
    private var selectedDate: Long = System.currentTimeMillis()
    private val PERMISSION_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calender)

        // Check for notification permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), PERMISSION_REQUEST_CODE)
            }
        }

        // Initialize views
        calendarView = findViewById(R.id.calendarView) // Update to your CalendarView ID
        selectedDateTextView = findViewById(R.id.selectedDateTextView)
        addTaskButton = findViewById(R.id.addTaskButton)
        taskRecyclerView = findViewById(R.id.taskRecyclerView)

        // Bottom Navigation Setup
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.page_1
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 -> {
                    startActivity(Intent(this, homepage::class.java))
                    true
                }
                R.id.page_2 -> true
                R.id.page_3 -> {
                    startActivity(Intent(this, Tasklist::class.java))
                    true
                }
                R.id.page_4 -> {
                    startActivity(Intent(this, Timer::class.java))
                    true
                }
                R.id.page_5 -> {
                    startActivity(Intent(this, profile::class.java))
                    true
                }
                else -> false
            }
        }





        // Initialize Task Manager
        taskManager = TaskManager(this)

        // Set up RecyclerView
        taskRecyclerView.layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(taskManager.getTasksForDate(selectedDate))
        taskRecyclerView.adapter = taskAdapter

        // Set default selected date as today
        selectedDateTextView.text = formatDate(selectedDate)

        // Calendar View listener for selecting date
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }.timeInMillis
            selectedDateTextView.text = formatDate(selectedDate)
            taskAdapter.updateTasks(taskManager.getTasksForDate(selectedDate))
        }

        // Add Task button click listener
        addTaskButton.setOnClickListener {
            showAddTaskDialog()
        }
    }

    // Show dialog to add a new task
    private fun showAddTaskDialog() {
        val dialogView = layoutInflater.inflate(R.layout.activity_calender_note, null)
        val noteEditText = dialogView.findViewById<EditText>(R.id.noteEditText)
        val timePicker = dialogView.findViewById<android.widget.TimePicker>(R.id.timePicker)

        AlertDialog.Builder(this)
            .setTitle("Add Task")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val taskName = noteEditText.text.toString()
                val hour = timePicker.hour
                val minute = timePicker.minute

                if (taskName.isNotEmpty()) {
                    val task = Task(taskName, selectedDate, hour, minute)
                    taskManager.addTask(task)
                    taskAdapter.updateTasks(taskManager.getTasksForDate(selectedDate))
                    setReminder(task)
                } else {
                    Toast.makeText(this, "Please enter a task name.", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    // Set a reminder using AlarmManager
    private fun setReminder(task: Task) {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = task.date
        calendar.set(Calendar.HOUR_OF_DAY, task.hour)
        calendar.set(Calendar.MINUTE, task.minute)

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java).apply {
            putExtra("taskTitle", task.title)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            this, task.id, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

    // Handle permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can now post notifications
            } else {
                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Helper function to format date
    private fun formatDate(timeInMillis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return "$day/$month/$year"
    }
}
