package com.example.stylish.models

import com.google.firebase.auth.FirebaseUser

data class SignInResult(
    val data: UserModel?,
    val errorMessage: String? = null,
)
