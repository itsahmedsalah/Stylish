package com.example.stylish.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object MyFireBase {

    val auth = FirebaseAuth.getInstance()
    val realTimeDB = Firebase.database.reference

    const val USERS = "users"

}