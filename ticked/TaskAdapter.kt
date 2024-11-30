package com.example.ticked.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ticked.R
import com.example.ticked.model.Task
import java.util.Calendar

class TaskAdapter(private var tasks: List<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)

    }

    override fun getItemCount(): Int = tasks.size

    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }

    fun removeTask(position: Int) {
        val updatedTasks = tasks.toMutableList()
        updatedTasks.removeAt(position)
        tasks = updatedTasks
        notifyItemRemoved(position)
    }


    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskTitle: TextView = itemView.findViewById(R.id.taskTitle)
        private val taskTime: TextView = itemView.findViewById(R.id.taskTime)
        private val taskDate: TextView = itemView.findViewById(R.id.taskDate) // New TextView for date

        fun bind(task: Task) {
            taskTitle.text = task.title
            taskTime.text = "${task.hour}:${if (task.minute < 10) "0" else ""}${task.minute}"
            taskDate.text = formatDate(task.date) // Format date for display
        }

        // Helper function to format date
        private fun formatDate(timeInMillis: Long): String {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timeInMillis
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1 // Months are 0-based
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            return "$day/$month/$year" // Adjust format as needed
        }


    }
}

