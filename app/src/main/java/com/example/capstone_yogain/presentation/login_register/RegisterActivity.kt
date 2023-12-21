package com.example.capstone_yogain.presentation.login_register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.capstone_yogain.R
import com.example.capstone_yogain.data.model.UserModel
import com.example.capstone_yogain.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private var _binding : ActivityRegisterBinding ?= null
    private val binding get() = _binding

    private val viewModel : UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.hide()

        showLoading(false)

        binding?.tvToLogin?.setOnClickListener{startActivity(Intent(this, LoginActivity::class.java))}
        binding?.btnRegister?.setOnClickListener {
            showLoading(true)
            val name = binding!!.nameEditText.text.toString()
            val email = binding!!.emailEditText.text.toString()
            val password = binding!!.passwordEditText.text.toString()

            if (name.isBlank()  && password.isBlank()){
                binding!!.nameEditText.error = getString(R.string.empty_name)
            }else if(email.isBlank()){
                binding!!.emailEditText.error = getString(R.string.empty_email)
            }else if(password.isBlank()){
                binding!!.passwordEditText.error = getString(R.string.empty_password)
            }else if(password.length < 8){
                binding!!.passwordEditText.error = getString(R.string.password_more)
            }else{
                val user = UserModel(
                    name = name,
                    email = email,
                    password = password,
                )
                viewModel.insertUser(user)
                Toast.makeText(this, getString(R.string.register_success),Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}