package com.example.iisc_assignment.ui.theme.navigation

import android.bluetooth.BluetoothDevice
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.iisc_assignment.ui.theme.screens.ChargerPointConfiguration
import com.example.iisc_assignment.ui.theme.screens.ChargerTypeScreen
import com.example.iisc_assignment.ui.theme.screens.CommissioningScreen
import com.example.iisc_assignment.ui.theme.screens.DeviceListScreen
import com.example.iisc_assignment.ui.theme.screens.ExternalMeterRegisterScreen
import com.example.iisc_assignment.ui.theme.screens.HomePage
import com.example.iisc_assignment.ui.theme.screens.LedConfigurationScreen
import com.example.iisc_assignment.ui.theme.screens.LoginScreen
import com.example.iisc_assignment.ui.theme.screens.RfidScreen
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
    NavHost(navController = navController, startDestination = NavigationRoute.SPLASH) {

        // Splash
        composable(NavigationRoute.SPLASH) {
            SplashScreen(navController = navController)
        }

        // Login
        composable(NavigationRoute.LOGIN) {
            LoginScreen(navController = navController)
        }

        composable(NavigationRoute.HOME) {
            HomePage(navController = navController)
        }

        // Devices (Bluetooth scan result list)
        composable(NavigationRoute.DEVICES) {
            DeviceListScreen(
                devices = scanResults,
                onDeviceClick = { device ->
                    onDeviceClick(device)
                    navController.navigate(NavigationRoute.WIFI)
                },
                onScanClick = onScanClick,
                navController = navController
            )
        }

        // WiFi Config
        composable(NavigationRoute.WIFI) {
            WifiConfigScreen(
                wifiStatus = wifiStatus,
                onSend = onSend,
                navController = navController
            )
        }

        // ChargerPoint Configuration
        composable(NavigationRoute.CHARGER_POINT_CONFIG) {
            ChargerPointConfiguration(navController = navController)
        }
        composable(route = NavigationRoute.RFID) {
            RfidScreen(navController = navController)
        }
        composable(route = NavigationRoute.LEDConfiguration) {
            LedConfigurationScreen(navController = navController)
        }

        composable(route = NavigationRoute.ExternalMeterRegister) {
            ExternalMeterRegisterScreen(navController = navController)
        }

        composable(route = NavigationRoute.ChargerType) {
            ChargerTypeScreen(navController = navController)
        }

        composable(route = NavigationRoute.Commissioning) {
            CommissioningScreen(navController = navController)
        }
    }
}