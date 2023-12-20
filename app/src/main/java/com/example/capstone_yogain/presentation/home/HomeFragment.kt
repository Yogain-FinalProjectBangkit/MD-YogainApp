package com.example.capstone_yogain.presentation.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.example.capstone_yogain.data.model.PoseModel
import com.example.capstone_yogain.data.repo.PoseRepository
import com.example.capstone_yogain.databinding.FragmentHomeBinding
import com.example.capstone_yogain.presentation.ViewModelFactory
import com.example.capstone_yogain.presentation.detail.DetailActivity
import com.example.capstone_yogain.presentation.profile.ProfileViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: HomeAdapter
    private val profileViewModel : ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPrefs =
            requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        val email = sharedPrefs.getString("userEmail", "")
        if (email != null) {
            profileViewModel.getUserByEmail(email).observe(viewLifecycleOwner) { userModels ->
                if (!userModels.isNullOrEmpty()) {
                    val userModel = userModels[0]
                    binding.tvUser.text = userModel.name
                }
            }
        }

        val repository = PoseRepository.getInstance()
        viewModel = ViewModelProvider(this, ViewModelFactory(repository))[HomeViewModel::class.java]

        adapter = HomeAdapter() // Initialize the adapter

        binding.rvGrid1.adapter = adapter

        lifecycleScope.launch {
            viewModel.getPose().collect { poses ->
                // Do something with the list of poses
                adapter.submitData(PagingData.from(poses))
                adapter.onItemClickCallback = object : HomeAdapter.OnItemCLickCallback {
                    override fun onItemClicked(pose: PoseModel) {
                        // Handle item click, for example, start a new activity
                        val intent = Intent(requireContext(), DetailActivity::class.java)
                        intent.putExtra("POSE_ID", pose.id)
                        intent.putExtra("POSE_NAME", pose.poseName)
                        intent.putExtra("POSE_DESCRIPTION", pose.description)
                        intent.putExtra("POSE_IMAGE", pose.imagePose)
                        // Add more data as needed
                        startActivity(intent)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
