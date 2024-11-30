package com.example.ticked

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.ticked.model.Task
import com.example.ticked.data.TaskManager

class ReminderAdapter(
    private val reminders: MutableList<Task>, // Change to MutableList
    private val taskManager: TaskManager // Pass TaskManager to handle deletion
) : RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    // ViewHolder class to bind the views
    class ReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskTitle: TextView = itemView.findViewById(R.id.taskTitle)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton) // Delete button (trash icon)

        // Binding function to bind task details and handle delete click
        fun bind(task: Task, position: Int, adapter: ReminderAdapter, taskManager: TaskManager) {
            taskTitle.text = task.title

            // Handle delete click
            deleteButton.setOnClickListener {
                AlertDialog.Builder(itemView.context)
                    .setTitle("Delete Task")
                    .setMessage("Are you sure you want to delete this task?")
                    .setPositiveButton("Yes") { _, _ ->
                        // Remove task from TaskManager
                        taskManager.removeTask(task)
                        // Remove task from the list and update the adapter
                        adapter.removeReminder(position)
                    }
                    .setNegativeButton("No", null)
                    .show()
            }
        }
    }

    // Inflate the item view and create a ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reminder, parent, false)
        return ReminderViewHolder(view)
    }

    // Bind the ViewHolder with data
    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val task = reminders[position]
        holder.bind(task, position, this, taskManager)
    }

    // Return the total number of reminders
    override fun getItemCount(): Int {
        return reminders.size
    }

    // Function to remove a reminder from the list and notify the adapter
    private fun removeReminder(position: Int) {
        reminders.removeAt(position)  // Now works because reminders is MutableList
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, reminders.size)
    }
}

