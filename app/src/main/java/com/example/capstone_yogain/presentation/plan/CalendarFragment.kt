package com.example.capstone_yogain.presentation.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.WorkManager
import com.example.capstone_yogain.R
import com.example.capstone_yogain.data.model.TaskModel
import com.example.capstone_yogain.databinding.FragmentCalendarBinding


class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding
    private val taskViewModel: TaskViewModel by viewModels()
    private lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init adapter and set Model
        adapter = TaskAdapter(::taskAction)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = adapter

        taskViewModel.fetchAllTasks().observe(viewLifecycleOwner) { tasks ->
            if (tasks.isEmpty()) {
                binding.emptyText.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.emptyText.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                adapter.submitList(tasks)
            }
        }

        // init swipe to delete
        val swipeToDeleteCallback = SwipeToDeleteCallback(requireContext(), adapter)
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        binding.addTask.setOnClickListener{
            findNavController().navigate(R.id.addTaskFragment)
        }

        binding.tvPlan.text = getString(R.string.task_overview)
        }


    private fun taskAction(taskModel: TaskModel, tag: String){
        when(tag){
            "Edit" -> taskViewModel.updateTask(taskModel) // edit checkbox data from room
            "Delete" -> {
                taskViewModel.deleteTask(taskModel) // delete row data from room

                // cancel notification
                WorkManager.getInstance(requireContext()).cancelUniqueWork(taskModel.tittle)
            }
        }
    }
}