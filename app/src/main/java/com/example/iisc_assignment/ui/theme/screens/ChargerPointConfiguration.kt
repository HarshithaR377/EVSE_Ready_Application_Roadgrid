package com.example.iisc_assignment.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ChargerPointConfiguration() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    "Menu",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
                Divider()
                NavigationDrawerItem(
                    label = { Text("Home") },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() } }
                )
                NavigationDrawerItem(
                    label = { Text("Settings") },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() } }
                )
                NavigationDrawerItem(
                    label = { Text("About") },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() } }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("ChargerPoint Configuration") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open() else drawerState.close()
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { innerPadding ->
            // Your old column form content
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(state = rememberScrollState())
            ) {
                val modelName = remember { mutableStateOf("") }
                val serialNumber = remember { mutableStateOf("") }
                val vendor = remember { mutableStateOf("") }
                val websocketUrl = remember { mutableStateOf("") }
                val authorizationTocken = remember { mutableStateOf("") }
                val electricityUnitPrice = remember { mutableStateOf("") }
                val temperature = remember { mutableStateOf("") }
                val rfidTagLength = remember { mutableStateOf("") }
                val doNotStopSession = listOf("Yes", "No")
                val doNotStopSessionListener = remember { mutableStateOf("") }
                val selectMaxConnector = listOf("1", "2")
                val selectMaxConnectorListener = remember { mutableStateOf("") }

                var expandedDoNotStop by remember { mutableStateOf(false) }
                var expandedMaxConnector by remember { mutableStateOf(false) }

                OutlinedTextField(
                    value = modelName.value,
                    onValueChange = { modelName.value = it },
                    label = { Text("Model Name") },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
                )

                OutlinedTextField(
                    value = serialNumber.value,
                    onValueChange = { serialNumber.value = it },
                    label = { Text("Serial No") },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
                )

                OutlinedTextField(
                    value = vendor.value,
                    onValueChange = { vendor.value = it },
                    label = { Text("Vendor") },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
                )

                OutlinedTextField(
                    value = websocketUrl.value,
                    onValueChange = { websocketUrl.value = it },
                    label = { Text("Websocket URL") },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
                )

                OutlinedTextField(
                    value = authorizationTocken.value,
                    onValueChange = { authorizationTocken.value = it },
                    label = { Text("Authorization Token") },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
                )

                OutlinedTextField(
                    value = electricityUnitPrice.value,
                    onValueChange = { electricityUnitPrice.value = it },
                    label = { Text("Electricity Unit Price") },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
                )

                OutlinedTextField(
                    value = temperature.value,
                    onValueChange = { temperature.value = it },
                    label = { Text("Temperature (°C)") },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
                )

                OutlinedTextField(
                    value = rfidTagLength.value,
                    onValueChange = { rfidTagLength.value = it },
                    label = { Text("RFID Tag Length") },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
                )

                Text(
                    "Do not stop session",
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                )
                ExposedDropdownMenuBox(
                    expanded = expandedDoNotStop,
                    onExpandedChange = { expandedDoNotStop = !expandedDoNotStop }
                ) {
                    OutlinedTextField(
                        value = doNotStopSessionListener.value,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Do not stop session") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDoNotStop)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth().padding(horizontal = 10.dp)
                    )

                    ExposedDropdownMenu(
                        expanded = expandedDoNotStop,
                        onDismissRequest = { expandedDoNotStop = false }
                    ) {
                        doNotStopSession.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    doNotStopSessionListener.value = option
                                    expandedDoNotStop = false
                                }
                            )
                        }
                    }
                }

                Text("Select Max Connector", modifier = Modifier.padding(start = 10.dp, top = 10.dp))
                ExposedDropdownMenuBox(
                    expanded = expandedMaxConnector,
                    onExpandedChange = { expandedMaxConnector = !expandedMaxConnector }
                ) {
                    OutlinedTextField(
                        value = selectMaxConnectorListener.value,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Select Max Connector") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedMaxConnector)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth().padding(horizontal = 10.dp)
                    )

                    ExposedDropdownMenu(
                        expanded = expandedMaxConnector,
                        onDismissRequest = { expandedMaxConnector = false }
                    ) {
                        selectMaxConnector.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    selectMaxConnectorListener.value = option
                                    expandedMaxConnector = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
