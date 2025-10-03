package com.example.iisc_assignment.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExternalMeterRegisterScreen(navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    // Dropdown options
    val endianOptions = listOf("Little Endian", "Big Endian")
    val parityOptions = listOf("None", "Even", "Odd")
    val stopBitOptions = listOf("1", "2")
    val dataBitsOptions = listOf("7", "8")

    // Meter 1 state
    var rVoltage1 by remember { mutableStateOf("") }
    var yVoltage1 by remember { mutableStateOf("") }
    var bVoltage1 by remember { mutableStateOf("") }
    var rCurrent1 by remember { mutableStateOf("") }
    var yCurrent1 by remember { mutableStateOf("") }
    var bCurrent1 by remember { mutableStateOf("") }
    var totalActivePower1 by remember { mutableStateOf("") }
    var totalActiveEnergy1 by remember { mutableStateOf("") }
    var meterSlaveId1 by remember { mutableStateOf("") }
    var endian1 by remember { mutableStateOf("") }
    var expandedEndian1 by remember { mutableStateOf(false) }

    // Meter 2 state
    var rVoltage2 by remember { mutableStateOf("") }
    var yVoltage2 by remember { mutableStateOf("") }
    var bVoltage2 by remember { mutableStateOf("") }
    var rCurrent2 by remember { mutableStateOf("") }
    var yCurrent2 by remember { mutableStateOf("") }
    var bCurrent2 by remember { mutableStateOf("") }
    var totalActivePower2 by remember { mutableStateOf("") }
    var totalActiveEnergy2 by remember { mutableStateOf("") }
    var meterSlaveId2 by remember { mutableStateOf("") }
    var endian2 by remember { mutableStateOf("") }
    var expandedEndian2 by remember { mutableStateOf(false) }

    // Modbus Parameter state
    var parityBit by remember { mutableStateOf("") }
    var expandedParity by remember { mutableStateOf(false) }
    var stopBit by remember { mutableStateOf("") }
    var expandedStopBit by remember { mutableStateOf(false) }
    var readInputRegister by remember { mutableStateOf("0x4") }
    var dataBits by remember { mutableStateOf("8") }
    var expandedDataBits by remember { mutableStateOf(false) }

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
                    title = { Text("External Meter Register") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { if (drawerState.isClosed) drawerState.open() else drawerState.close() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Meter 1
                Text(
                    "Meter 1: Register Configuration",
                    style = MaterialTheme.typography.titleMedium
                )
                OutlinedTextField(
                    value = rVoltage1,
                    onValueChange = { rVoltage1 = it },
                    label = { Text("Enter R Phase Voltage Register") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = yVoltage1,
                    onValueChange = { yVoltage1 = it },
                    label = { Text("Enter Y Phase Voltage Register") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = bVoltage1,
                    onValueChange = { bVoltage1 = it },
                    label = { Text("Enter B Phase Voltage Register") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = rCurrent1,
                    onValueChange = { rCurrent1 = it },
                    label = { Text("Enter R Phase Current Register") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = yCurrent1,
                    onValueChange = { yCurrent1 = it },
                    label = { Text("Enter Y Phase Current Register") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = bCurrent1,
                    onValueChange = { bCurrent1 = it },
                    label = { Text("Enter B Phase Current Register") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = totalActivePower1,
                    onValueChange = { totalActivePower1 = it },
                    label = { Text("Enter Total Active Power Register") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = totalActiveEnergy1,
                    onValueChange = { totalActiveEnergy1 = it },
                    label = { Text("Enter Total Active Energy Register") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = meterSlaveId1,
                    onValueChange = { meterSlaveId1 = it },
                    label = { Text("Enter Meter Slave ID") },
                    modifier = Modifier.fillMaxWidth()
                )

                Text("1. Little Endian / 2. Big Endian")
                ExposedDropdownMenuBox(
                    expanded = expandedEndian1,
                    onExpandedChange = { expandedEndian1 = !expandedEndian1 }) {
                    OutlinedTextField(
                        value = endian1,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Endian Type") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedEndian1) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedEndian1,
                        onDismissRequest = { expandedEndian1 = false }) {
                        endianOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = { endian1 = option; expandedEndian1 = false })
                        }
                    }
                }
                Text(
                    "Meter 2: Register Configuration",
                    style = MaterialTheme.typography.titleMedium
                )
                OutlinedTextField(
                    value = rVoltage2,
                    onValueChange = { rVoltage2 = it },
                    label = { Text("Enter R Phase Voltage Register") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = yVoltage2,
                    onValueChange = { yVoltage2 = it },
                    label = { Text("Enter Y Phase Voltage Register") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = bVoltage2,
                    onValueChange = { bVoltage2 = it },
                    label = { Text("Enter B Phase Voltage Register") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = rCurrent2,
                    onValueChange = { rCurrent2 = it },
                    label = { Text("Enter R Phase Current Register") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = yCurrent2,
                    onValueChange = { yCurrent2 = it },
                    label = { Text("Enter Y Phase Current Register") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = bCurrent2,
                    onValueChange = { bCurrent2 = it },
                    label = { Text("Enter B Phase Current Register") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = totalActivePower2,
                    onValueChange = { totalActivePower2 = it },
                    label = { Text("Enter Total Active Power Register") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = totalActiveEnergy2,
                    onValueChange = { totalActiveEnergy2 = it },
                    label = { Text("Enter Total Active Energy Register") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = meterSlaveId2,
                    onValueChange = { meterSlaveId2 = it },
                    label = { Text("Enter Meter Slave ID") },
                    modifier = Modifier.fillMaxWidth()
                )

                Text("1. Little Endian / 2. Big Endian")
                ExposedDropdownMenuBox(
                    expanded = expandedEndian2,
                    onExpandedChange = { expandedEndian2 = !expandedEndian2 }) {
                    OutlinedTextField(
                        value = endian2,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Endian Type") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedEndian2) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedEndian2,
                        onDismissRequest = { expandedEndian2 = false }) {
                        endianOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = { endian2 = option; expandedEndian2 = false })
                        }
                    }
                }

                // Modbus Parameter
                Text("Modbus Parameter", style = MaterialTheme.typography.titleMedium)
                Text("Note: This will apply for both external meters")

                // Parity Bit
                ExposedDropdownMenuBox(
                    expanded = expandedParity,
                    onExpandedChange = { expandedParity = !expandedParity }) {
                    OutlinedTextField(
                        value = parityBit,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Parity Bit") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedParity) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedParity,
                        onDismissRequest = { expandedParity = false }) {
                        parityOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = { parityBit = option; expandedParity = false })
                        }
                    }
                }

                // Stop Bit
                ExposedDropdownMenuBox(
                    expanded = expandedStopBit,
                    onExpandedChange = { expandedStopBit = !expandedStopBit }) {
                    OutlinedTextField(
                        value = stopBit,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Stop Bit") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedStopBit) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedStopBit,
                        onDismissRequest = { expandedStopBit = false }) {
                        stopBitOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = { stopBit = option; expandedStopBit = false })
                        }
                    }
                }
                OutlinedTextField(
                    value = readInputRegister,
                    onValueChange = { readInputRegister = it },
                    label = { Text("Function Code: Read Input Register (0x4)") },
                    modifier = Modifier.fillMaxWidth()
                )
                ExposedDropdownMenuBox(
                    expanded = expandedDataBits,
                    onExpandedChange = { expandedDataBits = !expandedDataBits }) {
                    OutlinedTextField(
                        value = dataBits,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Data Bits") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDataBits) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedDataBits,
                        onDismissRequest = { expandedDataBits = false }) {
                        dataBitsOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = { dataBits = option; expandedDataBits = false })
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(onClick = { }, modifier = Modifier.weight(1f)) { Text("Skip") }
                    Button(onClick = { }, modifier = Modifier.weight(1f)) { Text("Save") }
                }
            }
        }
    }
}
