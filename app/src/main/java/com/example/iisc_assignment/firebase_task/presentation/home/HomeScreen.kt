package com.example.iisc_assignment.firebase_task.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// This is a HomeScreen composable. It showing after login screen.
// User can see welcome message and button for logout.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    // This function will call when user click logout icon in top bar
    onLogoutClick: (() -> Unit)? = null
) {
    // Scaffold is main layout. It using TopAppBar and content body.
    Scaffold(
        topBar = {
            // Top bar with title and logout arrow icon
            TopAppBar(
                title = {
                    Text(
                        text = "Home", // show screen title
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                actions = {
                    // If logout function is not null, show logout icon
                    if (onLogoutClick != null) {
                        IconButton(onClick = onLogoutClick) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack, // arrow used as logout icon
                                contentDescription = "Logout",
                                tint = Color.White
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1565C0) // dark blue color
                )
            )
        }
    ) { padding ->
        // Main content box with background color and center align text
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFE3F2FD)), // light blue bg
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Big welcome text
                Text(
                    text = " Welcome to IISc App", // extra space added on purpose
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0D47A1),
                        fontSize = 26.sp
                    ),
                    modifier = Modifier.padding(16.dp)
                )

                // Second text to tell user what they can do in app
                Text(
                    text = "Explore courses, take tests, and grow your skills.", // tell user what to do
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }
        }
    }
}
