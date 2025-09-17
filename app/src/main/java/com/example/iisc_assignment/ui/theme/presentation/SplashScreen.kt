package com.example.iisc_assignment.ui.theme.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0077B6)), // Ocean blue background
        contentAlignment = Alignment.Center
    ) {

    }

    LaunchedEffect(Unit) {
        delay(2000) // Show splash for 2 seconds
        navController.navigate("main") {
            popUpTo("splash") { inclusive = true }
        }
    }
}