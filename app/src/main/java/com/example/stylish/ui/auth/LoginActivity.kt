package com.example.stylish.ui.auth

import android.content.Intent
import android.os.Bundle

import android.widget.Toast
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity

import com.example.stylish.databinding.ActivityLoginBinding
import com.example.stylish.models.UserModel
import com.example.stylish.ui.homepage.MainActivity


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val authViewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authViewModel.signUpSuccess.observe(this) {
            if (it) {
                Toast.makeText(this, "Welcome Back", Toast.LENGTH_LONG).show()
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_LONG).show()
            }
        }
        authViewModel.signUpError.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }


        onClick()

    }

    private fun onClick() {
        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text?.trim().toString()
            val password = binding.etPassword.text!!.trim().toString()
            validation(email, password)
        }
    }

    private fun validation(email: String, password: String) {
        if (email.isEmpty()) {
            binding.etEmail.error = "Fill this field probably "
        } else if (password.isEmpty()) {
            binding.etPassword.error = "Fill this field probably "
        } else {
            login(email, password)
        }

    }

    private fun login(email: String, password: String) {
        authViewModel.signIn(UserModel(null, null, email,null ),password)
    }

}
