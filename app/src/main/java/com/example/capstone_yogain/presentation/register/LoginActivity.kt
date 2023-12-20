package com.example.capstone_yogain.presentation.register

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.capstone_yogain.R
import com.example.capstone_yogain.databinding.ActivityLoginBinding
import com.example.capstone_yogain.presentation.main.MainActivity
import com.example.capstone_yogain.utils.showToast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {
    private var _binding : ActivityLoginBinding ?= null
    private val binding get() = _binding
    private val viewModel : UserViewModel by viewModels()

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.hide()

        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth

        val sharedPrefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        // Mendapatkan editor SharedPreferences
        val editor = sharedPrefs.edit()

        binding?.tvToRegister?.setOnClickListener{
            val intentToRegister = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intentToRegister)
        }
        binding?.btnLogin?.setOnClickListener {
            showLoading(true)
            val email = binding?.emailEditText?.text.toString().trim()
            val password = binding?.passwordEditText?.text.toString().trim()

            // Check if email and password are not empty
            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Call the login function in the ViewModel
                viewModel.loginUser(email, password)
                editor.putString("userEmail", email)
                editor.putString("userPassword", password)
                editor.apply()
                showLoading(false)
                viewModel.loginResult.observe(this) { loginResult ->
                    if (loginResult) {
                        showToast(this, R.string.login_success)
                        // Navigate to the main activity or perform other actions
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    // Handle the case where login failed if needed
                }
            } else {
                Toast.makeText(this, getString(R.string.please_enter_email_password), Toast.LENGTH_SHORT).show()
                showLoading(false)
            }
        }

        binding?.signInButton?.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try{
                val account = task.getResult(ApiException::class.java)!!
                showToast(this, R.string.firebaseAuthWithGoogle)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                showToast(this, R.string.firebaseAuthWithGoogleFailed)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    showToast(this,R.string.signInCredientialSuccess)
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    showToast(this,R.string.signInCredientialFailure)
                    updateUI(null)
                }
            }
    }
    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null){
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
    }

    companion object {
        private const val TAG = "LoginActivity"
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}