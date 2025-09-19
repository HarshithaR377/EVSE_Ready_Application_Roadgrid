package com.example.iisc_assignment.ui.theme.navigation

import android.bluetooth.BluetoothDevice
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.iisc_assignment.ui.theme.screens.DeviceListScreen
import com.example.iisc_assignment.ui.theme.screens.LoginScreen
import com.example.iisc_assignment.ui.theme.screens.SplashScreen
import com.example.iisc_assignment.ui.theme.screens.WifiConfigScreen
@Composable
fun AppNavigation(
    navController: NavHostController,
    scanResults: List<BluetoothDevice>,
    onDeviceClick: (BluetoothDevice) -> Unit,
    onScanClick: () -> Unit,
    wifiStatus: String,
    onSend: (String, String) -> Unit
) {
    NavHost(navController = navController, startDestination = "splash") {

        // Splash
        composable("splash") {
            SplashScreen(navController = navController)
        }

        // Login
        composable("login") {
            LoginScreen(navController = navController)
        }

        // Devices (Bluetooth scan result list)
        composable("devices") {
            DeviceListScreen(
                devices = scanResults,
                onDeviceClick = { device ->
                    onDeviceClick(device)
                    navController.navigate("wifi")
                },
                onScanClick = onScanClick,
                navController = navController
            )
        }

        // WiFi Config
        composable("wifi") {
            WifiConfigScreen(
                wifiStatus = wifiStatus,
                onSend = onSend,
                navController = navController
            )
        }
    }
}