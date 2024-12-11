package com.example.stylish.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stylish.R
import com.example.stylish.models.UserModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val authRepository = AuthRepository()
    private val _signUpSuccess = MutableLiveData<Boolean>()
    val signUpSuccess: LiveData<Boolean> get() = _signUpSuccess

    private val _signUpError = MutableLiveData<String>()
    val signUpError: LiveData<String> get() = _signUpError

    fun signUp(user: UserModel, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val success = authRepository.signUp(user, password)
                delay(1000)
                if (success) {
                    _signUpSuccess.postValue(true)
                } else {
                    _signUpError.postValue("Invalid Email or Password")
                }
            } catch (e: Exception) {
                _signUpError.postValue("Error : ${e.message}")
            }
        }
    }

    fun signIn(user: UserModel, password: String) {
        viewModelScope.launch(Dispatchers.IO) {

            try {
                val success = authRepository.signIn(user, password)
                if (success) {
                    _signUpSuccess.postValue(true)
                } else {
                    _signUpError.postValue("Invalid Email or Password")
                }
            } catch (e: Exception) {
                _signUpError.postValue("Error : ${e.message}")
            }

        }
    }

    fun signUpWithGoogle() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val googleIdOption =
                    GetGoogleIdOption.Builder().setFilterByAuthorizedAccounts(false)
            } catch (e: Exception) {


            }
        }
    }

}