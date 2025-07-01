package com.example.iisc_assignment.firebase_task.presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

/**
 * Composable function that displays the Splash Screen of the IISc App.
 *
 * - Shows a branded loading screen with the app name and a progress indicator.
 * - Automatically navigates to the Onboarding screen after a 2-second delay.
 *
 * @param navController NavController used to navigate from the splash screen to the onboarding screen.
 */
@Composable
fun SplashScreen(navController: NavController) {

    // Launches a coroutine to delay for 2 seconds and navigate to the onboarding screen
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("onboarding") {
            // Remove Splash screen from the back stack
            popUpTo("splash") { inclusive = true }
        }
    }

    // Splash UI Layout
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1565C0)), // App theme background color
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // App Name
            Text(
                text = "IISc App",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Loading Indicator
            CircularProgressIndicator(color = Color.White)
        }
    }
}
