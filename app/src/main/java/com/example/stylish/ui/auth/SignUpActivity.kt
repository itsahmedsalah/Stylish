package com.example.stylish.ui.auth

import android.content.Context
import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.lifecycleScope

import com.example.stylish.R


import com.example.stylish.databinding.ActivitySignUpBinding
import com.example.stylish.models.UserModel
import com.example.stylish.ui.auth.cloudauth.GoogleAuthUiClient
import com.example.stylish.ui.homepage.MainActivity
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


class SignUpActivity : AppCompatActivity(), ChooseAvatarDialog.OnAvatarSelectedListener {

    private lateinit var binding: ActivitySignUpBinding
    private var image: String? = null
    private val authViewModel: AuthViewModel by viewModels()

    private val googleIdClient by lazy {
        GoogleAuthUiClient(
            applicationContext,
            Identity.getSignInClient(applicationContext),
        )
    }


    private lateinit var launcher: ActivityResultLauncher<IntentSenderRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        launcher = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
            if (it.resultCode == RESULT_OK) {
                lifecycleScope.launch {
                    val intent = it.data ?: return@launch
                    val signInResult = googleIdClient.signInWithIntent(intent)
                    val intent2 = Intent(this@SignUpActivity,MainActivity::class.java)
                    startActivity(intent2)
                    signInResult.data?.let {
                        googleIdClient.getSignedInUser()

                    }
                }
            }
        }


        onCLick()
        authViewModel.signUpSuccess.observe(this) {
            if (it) {
                Toast.makeText(this@SignUpActivity, "Welcome Onboard", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_LONG).show()
            }
        }
        authViewModel.signUpError.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }

    }

    private fun onCLick() {
        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }



        binding.btnCreateAccount.setOnClickListener {
            if (image == null) {
                val customDialog = ChooseAvatarDialog(this)
                customDialog.avatarSelectedListener = this
                customDialog.window?.setBackgroundDrawable(
                    AppCompatResources.getDrawable(
                        this@SignUpActivity,
                        R.drawable.dialog_bg
                    )
                )
                customDialog.show()
                Log.d("", "onCLick: ")
            } else {
                Log.d("TAG", "onCLick: ")
            }


        }



        binding.btnGoogle.setOnClickListener {

            lifecycleScope.launch {
                try {
                    // Get the IntentSender for the Google Sign-In flow
                    val intentSender = googleIdClient.signIn()
                    if (intentSender != null) {
                        // Build the IntentSenderRequest and launch it
                        val request = IntentSenderRequest.Builder(intentSender).build()
                        launcher.launch(request)
                    } else {
                        Toast.makeText(
                            this@SignUpActivity,
                            "Unable to start Google Sign-In",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(
                        this@SignUpActivity,
                        "Google Sign-In failed: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun validation(
        name: String,
        email: String,
        password: String,
        confirmPass: String,
        image: String,
    ) {

        if (name.isEmpty() || email.isEmpty() || password.length < 6 || password != confirmPass) {
            Toast.makeText(this, "Fill the Field Probbley", Toast.LENGTH_LONG).show()
        } else {
            authViewModel.signUp(UserModel(null, name, email, image), password)
        }

    }

    override fun onAvatarSelected(avatarImage: String) {
        image = avatarImage
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text!!.trim().toString()
        val password = binding.etPassword.text?.trim().toString()
        val confirmPass = binding.etConfiremPassword.text?.trim().toString()
        validation(name, email, password, confirmPass, image!!)
    }


}

