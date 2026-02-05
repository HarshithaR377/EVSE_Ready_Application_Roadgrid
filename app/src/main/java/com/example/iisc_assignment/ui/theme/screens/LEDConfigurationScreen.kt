package com.example.iisc_assignment.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.iisc_assignment.ui.theme.navigation.NavigationRoute
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LedConfigurationScreen(navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    "Menu",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )
                NavigationDrawerItem(
                    label = { Text("Home") },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() } })
                NavigationDrawerItem(
                    label = { Text("Settings") },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() } })
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("LED Configuration") },
                    navigationIcon = {
                        IconButton(
                            onClick = { scope.launch { if (drawerState.isClosed) drawerState.open() else drawerState.close() } }
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { innerPadding ->

            val scrollState = rememberScrollState()

            // State lists
            val ledStates = listOf("Off", "On", "Blink")

            // Remember dropdown selections
            var availableBlue by remember { mutableStateOf("") }
            var preparingBlue by remember { mutableStateOf("") }
            var chargingGreen by remember { mutableStateOf("") }
            var chargingFinishGreen by remember { mutableStateOf("") }
            var faultRed by remember { mutableStateOf("") }
            var connectingRed by remember { mutableStateOf("") }
            var internetRed by remember { mutableStateOf("") }

            // Dropdown expanded states
            var expandedAvailable by remember { mutableStateOf(false) }
            var expandedPreparing by remember { mutableStateOf(false) }
            var expandedCharging by remember { mutableStateOf(false) }
            var expandedChargingFinish by remember { mutableStateOf(false) }
            var expandedFault by remember { mutableStateOf(false) }
            var expandedConnecting by remember { mutableStateOf(false) }
            var expandedInternet by remember { mutableStateOf(false) }

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Available State
                Text("Available State")
                ExposedDropdownMenuBox(
                    expanded = expandedAvailable,
                    onExpandedChange = { expandedAvailable = !expandedAvailable }
                ) {
                    OutlinedTextField(
                        value = availableBlue,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Blue Blink") },
                        // colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.Blue, textColor = Color.White),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedAvailable) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedAvailable,
                        onDismissRequest = { expandedAvailable = false }
                    ) {
                        ledStates.forEach {
                            DropdownMenuItem(text = { Text(it) }, onClick = {
                                availableBlue = it
                                expandedAvailable = false
                            })
                        }
                    }
                }

                // Preparing State
                Text("Preparing State")
                ExposedDropdownMenuBox(
                    expanded = expandedPreparing,
                    onExpandedChange = { expandedPreparing = !expandedPreparing }
                ) {
                    OutlinedTextField(
                        value = preparingBlue,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Blue Steady") },
                        // colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.Blue, textColor = Color.White),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPreparing) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedPreparing,
                        onDismissRequest = { expandedPreparing = false }
                    ) {
                        ledStates.forEach {
                            DropdownMenuItem(text = { Text(it) }, onClick = {
                                preparingBlue = it
                                expandedPreparing = false
                            })
                        }
                    }
                }

                // Charging State
                Text("Charging State")
                ExposedDropdownMenuBox(
                    expanded = expandedCharging,
                    onExpandedChange = { expandedCharging = !expandedCharging }
                ) {
                    OutlinedTextField(
                        value = chargingGreen,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Green Steady") },
                        //  colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.Green, textColor = Color.White),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCharging) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedCharging,
                        onDismissRequest = { expandedCharging = false }
                    ) {
                        ledStates.forEach {
                            DropdownMenuItem(text = { Text(it) }, onClick = {
                                chargingGreen = it
                                expandedCharging = false
                            })
                        }
                    }
                }

                // Charging Finish State
                Text("Charging Finish State")
                ExposedDropdownMenuBox(
                    expanded = expandedChargingFinish,
                    onExpandedChange = { expandedChargingFinish = !expandedChargingFinish }
                ) {
                    OutlinedTextField(
                        value = chargingFinishGreen,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Green Blink") },
                        // colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.Green, textColor = Color.White),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedChargingFinish) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedChargingFinish,
                        onDismissRequest = { expandedChargingFinish = false }
                    ) {
                        ledStates.forEach {
                            DropdownMenuItem(text = { Text(it) }, onClick = {
                                chargingFinishGreen = it
                                expandedChargingFinish = false
                            })
                        }
                    }
                }

                // Fault State
                Text("Fault State")
                ExposedDropdownMenuBox(
                    expanded = expandedFault,
                    onExpandedChange = { expandedFault = !expandedFault }
                ) {
                    OutlinedTextField(
                        value = faultRed,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Red Steady") },
                        //     colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.Red, textColor = Color.White),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedFault) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedFault,
                        onDismissRequest = { expandedFault = false }
                    ) {
                        ledStates.forEach {
                            DropdownMenuItem(text = { Text(it) }, onClick = {
                                faultRed = it
                                expandedFault = false
                            })
                        }
                    }
                }

                // Connecting to Server
                Text("Connecting to Server State")
                ExposedDropdownMenuBox(
                    expanded = expandedConnecting,
                    onExpandedChange = { expandedConnecting = !expandedConnecting }
                ) {
                    OutlinedTextField(
                        value = connectingRed,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Red Blink") },
                        //  colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.Red, textColor = Color.White),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedConnecting) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedConnecting,
                        onDismissRequest = { expandedConnecting = false }
                    ) {
                        ledStates.forEach {
                            DropdownMenuItem(text = { Text(it) }, onClick = {
                                connectingRed = it
                                expandedConnecting = false
                            })
                        }
                    }
                }


                // Connection to Internet
                Text("Connection to Internet State")
                ExposedDropdownMenuBox(
                    expanded = expandedInternet,
                    onExpandedChange = { expandedInternet = !expandedInternet }
                ) {
                    OutlinedTextField(
                        value = internetRed,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Red Steady") },
                        // colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.Red, textColor = Color.White),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedInternet) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedInternet,
                        onDismissRequest = { expandedInternet = false }
                    ) {
                        ledStates.forEach {
                            DropdownMenuItem(text = { Text(it) }, onClick = {
                                internetRed = it
                                expandedInternet = false
                            })
                        }
                    }
                }

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = { /* TODO: Skip action */ },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                    ) {
                        Text("Skip")
                    }

                    Button(
                        onClick = {
                            navController.navigate(NavigationRoute.ExternalMeterRegister) {
                                popUpTo(NavigationRoute.LEDConfiguration) { inclusive = true }
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Save & Next")
                    }
                }
            }
        }
    }
}
