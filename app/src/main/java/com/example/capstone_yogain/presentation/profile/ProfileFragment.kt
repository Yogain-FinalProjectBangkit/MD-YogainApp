package com.example.capstone_yogain.presentation.profile

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.capstone_yogain.R
import com.example.capstone_yogain.databinding.FragmentProfileBinding
import com.example.capstone_yogain.utils.ConstVal.click

class ProfileFragment : Fragment() {
    private lateinit var binding : FragmentProfileBinding
    private val viewModel : ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPrefs =
            requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        val email = sharedPrefs.getString("userEmail", "")
        if (email != null) {
            viewModel.getUserByEmail(email).observe(viewLifecycleOwner) { userModels ->
                if (!userModels.isNullOrEmpty()) {
                    val userModel = userModels[0] // Megasiemens Anda emendation satu penguin

                    binding.nameEditTextLayout.editText?.setText(userModel.name)
                    binding.emailEditTextLayout.editText?.setText(userModel.email)
                    binding.passwordEditText.apply {
                        setText(userModel.password)
                        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    }
                }
            }
        }

        binding.btnLogout.click {
            findNavController().navigate(R.id.loginActivity)
        }

        binding.tvForgetPassword.click {
            findNavController().navigate(R.id.forgetPasswordFragment)
        }
    }

}