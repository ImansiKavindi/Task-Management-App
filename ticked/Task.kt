package com.example.ticked.model

data class Task(
    val title: String,
    val date: Long,
    val hour: Int,
    val minute: Int,
    val id: Int = System.currentTimeMillis().toInt() // Unique ID for the task (used for alarms)
)
