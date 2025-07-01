package com.example.iisc_assignment.firebase_task.presentation.auth

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException

/**
 * Composable function that returns a remembered activity result launcher for Google Sign-In.
 *
 * - This launcher is used to start the Google Sign-In intent.
 * - On success, it returns the signed-in Google account to the provided `onSuccess` callback.
 * - On failure, it prints the exception stack trace.
 *
 * @param onSuccess A callback that receives the successfully signed-in [GoogleSignInAccount].
 * @return A launcher that can be used to initiate Google Sign-In via `launcher.launch(intent)`.
 */
@Composable
fun AuthLauncher(
    onSuccess: (account: GoogleSignInAccount) -> Unit
) = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
    try {
        val account = task.getResult(ApiException::class.java)
        onSuccess(account)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
