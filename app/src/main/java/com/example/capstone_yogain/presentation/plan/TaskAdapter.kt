package com.example.capstone_yogain.presentation.plan

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone_yogain.R
import com.example.capstone_yogain.data.model.TaskModel
import com.example.capstone_yogain.databinding.ItemTaskBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class TaskAdapter(val actionCallback: (TaskModel, String) -> Unit) : ListAdapter<TaskModel, TaskAdapter.TaskViewHolder>(TaskDiffCallback()){
    class TaskViewHolder(
        private val binding: ItemTaskBinding,
        val actionCallback: (TaskModel, String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(taskModel: TaskModel){
            binding.title.text = taskModel.tittle
            binding.description.text = taskModel.des
            binding.date.text = taskModel.date
            binding.time.text = taskModel.time
            binding.checkbox.isChecked = taskModel.isCompleted
            binding.checkbox.setOnClickListener {
                actionCallback(taskModel, "Edit")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding, actionCallback)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val taskModel = getItem(position)
        holder.bind(taskModel)
    }

    fun getTaskAtPosition(position: Int): TaskModel {
        return getItem(position)
    }
}

class TaskDiffCallback : DiffUtil.ItemCallback<TaskModel>(){
    override fun areItemsTheSame(oldItem: TaskModel, newItem: TaskModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TaskModel, newItem: TaskModel): Boolean {
        return oldItem == newItem
    }

}

class SwipeToDeleteCallback(
    private val context: Context,
    private val adapter: TaskAdapter,
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val taskModel = adapter.getTaskAtPosition(position)

        MaterialAlertDialogBuilder(context)
            .setIcon(R.drawable.ic_delete)
            .setTitle(R.string.are_you_sure)
            .setMessage(R.string.are_you_delete)
            .setNegativeButton("No") { _, _ ->
                // Reset item
                adapter.notifyItemChanged(position)
            }
            .setPositiveButton("Yes") { _, _ ->
                // Delete item
                adapter.actionCallback(taskModel, "Delete")
            }
            .show()
    }
}