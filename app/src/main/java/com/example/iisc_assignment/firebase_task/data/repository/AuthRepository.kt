package com.example.iisc_assignment.firebase_task.data.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

/**
 * Repository class that handles Firebase authentication logic.
 *
 * @param auth FirebaseAuth instance injected by Hilt.
 */
class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth
) {

    /**
     * Authenticates the user using the provided [credential].
     *
     * @param credential The [AuthCredential] obtained from Google Sign-In or other providers.
     * @param onSuccess Callback invoked when login is successful.
     * @param onError Callback invoked with an error message when login fails.
     */
    fun firebaseLoginWithCredential(
        credential: AuthCredential,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    // Passes error message to the error callback
                    onError(task.exception?.message ?: "Login failed")
                }
            }
    }

    /**
     * Returns the currently logged-in Firebase user, or null if no user is signed in.
     *
     * @return [FirebaseUser] instance or null.
     */
    fun getCurrentUser(): FirebaseUser? = auth.currentUser
}

