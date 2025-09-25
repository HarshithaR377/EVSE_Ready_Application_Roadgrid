package com.example.iisc_assignment.ui.theme.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import com.example.iisc_assignment.ui.theme.navigation.NavigationRoute
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WifiConfigScreen(
    wifiStatus: String,
    onSend: (String, String) -> Unit,
    navController: NavController
) {
    var ssid by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Dropdown state
    var interfaceType by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val interfaceOptions = listOf("Wi-Fi/GSM Configuration", "Ethernet")

    var appName by remember { mutableStateOf("") }
    var networkSwitchingApp by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Network Configuration") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Select Interface",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(Modifier.height(8.dp))

            // Interface type dropdown
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = interfaceType,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Interface Type") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    interfaceOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                interfaceType = option
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // Wi-Fi/GSM Config
            if (interfaceType == "Wi-Fi/GSM Configuration") {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Wi-Fi Configuration", style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(8.dp))

                        OutlinedTextField(
                            value = ssid,
                            onValueChange = { ssid = it },
                            leadingIcon = { Icon(Icons.Default.PlayArrow, contentDescription = "SSID") },
                            label = { Text("Wi-Fi Name (SSID)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password") },
                            label = { Text("Wi-Fi Password") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(16.dp))

                        Text("GSM Configuration", style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(
                            value = appName,
                            onValueChange = { appName = it },
                            leadingIcon = { Icon(Icons.Default.AddCircle, contentDescription = "App") },
                            label = { Text("App Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = networkSwitchingApp,
                            onValueChange = { networkSwitchingApp = it },
                            leadingIcon = { Icon(Icons.Default.Settings, contentDescription = "Network Switching") },
                            label = { Text("Network Switching Type") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            navController.navigate(NavigationRoute.Commissioning) {
                                popUpTo(NavigationRoute.WIFI) { inclusive = true }
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("Skip")
                    }

                    Button(
                        onClick = {
                            if (ssid.isNotBlank() && password.isNotBlank()) {
                                onSend(ssid, password)
                                Toast.makeText(context, "Wi-Fi credentials saved", Toast.LENGTH_SHORT).show()
                                navController.navigate(NavigationRoute.CHARGER_POINT_CONFIG) {
                                    popUpTo(NavigationRoute.WIFI) { inclusive = true }
                                }
                            } else {
                                Toast.makeText(context, "Enter SSID and Password", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Save & Next")
                    }
                }
            }

            // Ethernet Config
            if (interfaceType == "Ethernet") {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Ethernet selected – no extra configuration required.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            navController.navigate(NavigationRoute.DEVICES) {
                                popUpTo(NavigationRoute.WIFI) { inclusive = true }
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("Skip")
                    }

                    Button(
                        onClick = {
                            Toast.makeText(context, "Ethernet saved", Toast.LENGTH_SHORT).show()
                            navController.navigate(NavigationRoute.CHARGER_POINT_CONFIG) {
                                popUpTo(NavigationRoute.WIFI) { inclusive = true }
                            }
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Save & Next")
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            if (wifiStatus.isNotEmpty()) {
                Text("Wi-Fi Status: $wifiStatus", style = MaterialTheme.typography.bodyLarge)
            }
            Log.d("WiFiStatus", "Current Wi-Fi Status: $wifiStatus")
        }
    }
}
