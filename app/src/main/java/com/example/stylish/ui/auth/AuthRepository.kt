package com.example.stylish.ui.auth

import android.util.Log
import com.example.stylish.models.UserModel
import com.example.stylish.utils.MyFireBase
import com.example.stylish.utils.MySharedPreference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AuthRepository {

    suspend fun signUp(user: UserModel, password: String): Boolean {


        return suspendCancellableCoroutine { continuation ->
            MyFireBase.auth.createUserWithEmailAndPassword(user.userEmail!!, password)
                .addOnCompleteListener { task ->


                    if (task.isSuccessful) {
                        user.userId = MyFireBase.auth.uid
                        setUserDetails(user)
                        MyFireBase.realTimeDB.child(MyFireBase.USERS).child(user.userId!!)
                            .setValue(user)
                        continuation.resume(true)
                    } else {
                        continuation.resume(false)
                    }
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }

    suspend fun signIn(user: UserModel, password: String): Boolean {


        return suspendCancellableCoroutine { continuation ->
            MyFireBase.auth.signInWithEmailAndPassword(user.userEmail!!, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        user.userId = MyFireBase.auth.uid

                        MyFireBase.realTimeDB.child(MyFireBase.USERS)
                            .child(MyFireBase.auth.uid!!)
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {

                                    val _user = snapshot.getValue(UserModel::class.java)

                                    if (_user !=null){
                                        setUserDetails(_user!!)
                                        Log.d("", "onDataChange: ")
                                    }
                                    else{
                                        Log.d("", "onDataChange: ")
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    Log.d("TAG", "onCancelled: ")
                                }

                            })



                        continuation.resume(true)
                    } else {
                        continuation.resume(false)
                    }
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }

    private fun setUserDetails(
        user: UserModel,
    ) {
        MySharedPreference.setUserID(user.userId!!)
        MySharedPreference.setUserName(user.userName!!)
        MySharedPreference.setUserEmail(user.userEmail!!)
        MySharedPreference.setUserAvatar(user.userAvatar!!)
    }



}