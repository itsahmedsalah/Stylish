package com.example.stylish.ui.auth.cloudauth

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.example.stylish.R
import com.example.stylish.models.SignInResult
import com.example.stylish.models.UserModel
import com.example.stylish.utils.MyFireBase
import com.example.stylish.utils.MySharedPreference
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException

class GoogleAuthUiClient(private val context: Context, private val oneTapClient: SignInClient) {

    suspend fun signIn(): IntentSender? {

        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }

        return result?.pendingIntent?.intentSender
    }

    suspend fun signInWithIntent(intent: Intent): SignInResult {

        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)

        return try {
            val user = MyFireBase.auth.signInWithCredential(googleCredentials).await().user
            SignInResult(
                data = user?.run {
                    UserModel(
                        userId = uid,
                        userName = displayName,
                        userEmail = email,
                        userAvatar = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = e.message
            )
        }
    }


    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder().setGoogleIdTokenRequestOptions(
            GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(context.getString(R.string.default_web_client_id)).build()
        ).setAutoSelectEnabled(true)
            .build()
    }


    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            MyFireBase.auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    fun getSignedInUser() {
        MyFireBase.auth.currentUser?.run {
            MySharedPreference.setUserID(uid)
            MySharedPreference.setUserName(displayName.toString())
            MySharedPreference.setUserEmail(email.toString())
            MySharedPreference.setUserAvatar(photoUrl.toString())
        }

    }
}