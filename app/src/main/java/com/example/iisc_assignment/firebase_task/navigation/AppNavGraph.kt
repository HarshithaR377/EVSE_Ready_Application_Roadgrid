package com.example.iisc_assignment.firebase_task.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.iisc_assignment.firebase_task.presentation.auth.AuthViewModel
import com.example.iisc_assignment.firebase_task.presentation.auth.LoginScreen
import com.example.iisc_assignment.firebase_task.presentation.home.HomeScreen
import com.example.iisc_assignment.firebase_task.presentation.onboarding.OnboardingScreen
import com.example.iisc_assignment.firebase_task.presentation.splash.SplashScreen

/**
 * This is my navigation graph for auth flow. It have splash, onboarding, login and home screen.
 *
 * First it will show splash, then go to onboarding automatic. After that login screen is open.
 * When login success, it go to home screen.
 *
 * @param viewModel We use this for manage login states from LoginScreen.
 * @param navController This is for control navigation inside app.
 */
// AuthNavGraph.kt
@Composable
fun AuthNavGraph(
    viewModel: AuthViewModel,
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("onboarding") { OnboardingScreen(navController) }
        composable("login") { LoginScreen(navController, viewModel) }
        composable("home") { HomeScreen() }
    }
}