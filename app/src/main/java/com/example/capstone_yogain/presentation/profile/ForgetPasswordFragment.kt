package com.example.capstone_yogain.presentation.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.capstone_yogain.R
import com.example.capstone_yogain.databinding.FragmentForgetPasswordBinding
import kotlinx.coroutines.launch

class ForgetPasswordFragment : Fragment() {
    private lateinit var binding: FragmentForgetPasswordBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.btnChange.setOnClickListener {
          updatePassword()
        }
    }

    private fun updatePassword() {
        lifecycleScope.launch {
            val sharedPrefs = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val email = sharedPrefs.getString("userEmail", "")

            if (!email.isNullOrEmpty()) {
                val oldPassword = binding.oldPasswordEditText.text.toString()
                val newPassword = binding.newPasswordEditText.text.toString()
                viewModel.updatePassword(email, newPassword)
                Toast.makeText(
                    requireContext(),
                    R.string.change_password_succesfull,
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.profileFragment)
            } else {
                Toast.makeText(
                    requireContext(),
                    R.string.new_password_cannot_empty,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}