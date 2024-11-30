package com.example.ticked.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.ticked.AlarmReceiver
import com.example.ticked.Calender
import com.example.ticked.model.Task
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Calendar

class TaskManager(private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("taskPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun addTask(task: Task) {
        val tasks = getAllTasks().toMutableList()
        tasks.add(task)
        saveTasks(tasks)
    }

    fun getTasksForDate(date: Long): List<Task> {
        val targetDate = stripTimeFromDate(date)
        return getAllTasks().filter { stripTimeFromDate(it.date) == targetDate }
    }

    fun scheduleTaskNotification(task: Task) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra("taskTitle", task.title)

        val pendingIntent = PendingIntent.getBroadcast(context, task.title.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, task.date, pendingIntent) // task.date is the time in milliseconds
    }


    private fun stripTimeFromDate(timeInMillis: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    private fun getAllTasks(): List<Task> {
        val json = sharedPreferences.getString("tasks", null)
        return if (json != null) {
            val type = object : TypeToken<List<Task>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    private fun saveTasks(tasks: List<Task>) {
        val editor = sharedPreferences.edit()
        val json = gson.toJson(tasks)
        editor.putString("tasks", json)
        editor.apply()

        Log.d("TaskManager", "Tasks saved: $json")

    }


    fun removeTask(task: Task) {
        val tasks = getAllTasks().toMutableList() // Fetch all tasks and convert to mutable
        tasks.remove(task)  // Remove the task
        saveTasks(tasks)     // Save the updated task list
    }


}
