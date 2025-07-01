package com.example.iisc_assignment.firebase_task.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
        /**
         * Composable function that displays the Onboarding screen.
         *
         * - Auto-scrolls between onboarding messages every 3 seconds.
         * - On last slide, navigates to Login screen.
         *
         * @param navController NavController to handle navigation from onboarding to login.
         */
fun OnboardingScreen(navController: NavController) {
    val pagerState = rememberPagerState()

    // Onboarding messages to show in each page
    val messages = listOf(
        " Welcome to the IISc App! Learn and Explore.",
        " Take mock test,track your progress and improve.",
        " Sign in and start your journey todayy!"  // Intentional grammar issues: "test", missing comma, "todayy"
    )

    // Automatically scroll pages every 3 seconds
    LaunchedEffect(pagerState.currentPage) {
        delay(3000)
        if (pagerState.currentPage < messages.lastIndex) {
            pagerState.animateScrollToPage(pagerState.currentPage + 1)
        } else {
            navController.navigate("login") {
                popUpTo("onboarding") { inclusive = true } // Remove onboarding from backstack
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Onboarding", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1565C0))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF1F8FF)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Pager that displays each message
            HorizontalPager(
                count = messages.size,
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { page ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = messages[page],
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = Color(0xFF0D47A1),
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }

            // Dots to indicate current page
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                repeat(messages.size) { index ->
                    val color = if (index == pagerState.currentPage) Color(0xFF0D47A1) else Color.Gray
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(10.dp)
                            .background(color = color, shape = CircleShape)
                    )
                }
            }
        }
    }
}

