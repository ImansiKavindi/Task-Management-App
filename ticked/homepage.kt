package com.example.ticked

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ticked.data.TaskManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.*

class homepage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        // Set the date in a TextView
        val currentDateTime = SimpleDateFormat("EEEE, MMM d yyyy", Locale.getDefault()).format(Date())
        findViewById<TextView>(R.id.textView10).text = currentDateTime // Assuming textView10 is your date TextView.

        // RecyclerView for reminders
        val recyclerView = findViewById<RecyclerView>(R.id.remindersRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val taskManager = TaskManager(this)
        val currentDayInMillis = System.currentTimeMillis()
        val todayTasks = taskManager.getTasksForDate(currentDayInMillis)

        if (todayTasks.isNotEmpty()) {
            val adapter = ReminderAdapter(todayTasks.toMutableList(), taskManager)

            recyclerView.adapter = adapter
        } else {
            // Display "No reminders" text
            //findViewById<TextView>(R.id.textViewReminders).text = "No reminders for today."
        }

        // Bottom navigation setup
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.page_1
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 -> true
                R.id.page_2 -> {
                    startActivity(Intent(this, Calender::class.java))
                    true
                }
                R.id.page_3 -> {
                    startActivity(Intent(this, Tasklist::class.java))
                    true
                }
                R.id.page_4 -> {
                    startActivity(Intent(this, Timer::class.java))
                    true
                }
                R.id.page_5 -> {
                    startActivity(Intent(this, ContactsContract.Profile::class.java))
                    true
                }
                else -> false
            }
        }
    }
}
