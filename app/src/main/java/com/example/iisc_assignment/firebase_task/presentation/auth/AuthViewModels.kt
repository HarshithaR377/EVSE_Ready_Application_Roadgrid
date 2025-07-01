package com.example.iisc_assignment.firebase_task.presentation.auth

import androidx.lifecycle.ViewModel
import com.example.iisc_assignment.firebase_task.data.repository.AuthRepository
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * ViewModel responsible for handling authentication-related logic.
 * It acts as a bridge between the UI and [AuthRepository].
 *
 * @param authRepository The repository that handles Firebase authentication.
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    // Mutable state to track login status internally
    private val _loginSuccess = MutableStateFlow(false)

    /**
     * Public immutable state flow exposing login success status.
     * The UI layer collects this to respond to login events.
     */
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    /**
     * Attempts to log in the user using the given Firebase [AuthCredential].
     *
     * - On success, sets [_loginSuccess] to true.
     * - On failure, sets [_loginSuccess] to false.
     *
     * @param credential The [AuthCredential] (e.g., from Google Sign-In).
     */
    fun loginWithCredential(credential: AuthCredential) {
        authRepository.firebaseLoginWithCredential(
            credential = credential,
            onSuccess = {
                _loginSuccess.value = true
            },
            onError = {
                _loginSuccess.value = false
            }
        )
    }

    /**
     * Returns the currently logged-in Firebase user.
     *
     * @return The [FirebaseUser] instance, or null if not logged in.
     */
    fun getCurrentUser(): FirebaseUser? = authRepository.getCurrentUser()

    /**
     * Optional callback function triggered after login success.
     *
     * - Can be used to store login status or user info in DataStore.
     * - Currently placeholder for post-login actions.
     */
    fun onLoginSuccess() {
        // Save to DataStore or perform post-login tasks if needed
    }
}
