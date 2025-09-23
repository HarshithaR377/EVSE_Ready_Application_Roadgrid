package com.example.iisc_assignment.ui.theme.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Switch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.iisc_assignment.ui.theme.navigation.NavigationRoute
import kotlinx.coroutines.launch

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChargerPointConfiguration(navController: NavController = NavController(context = LocalContext.current)) {
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
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(10.dp)
            ) {
                val modelName = remember { mutableStateOf("") }
                val serialNumber = remember { mutableStateOf("") }
                val vendor = remember { mutableStateOf("") }
                val websocketUrl = remember { mutableStateOf("") }
                val authorizationToken = remember { mutableStateOf("") }
                val electricityUnitPrice = remember { mutableStateOf("") }
                val temperature = remember { mutableStateOf("") }
                val rfidTagLength = remember { mutableStateOf("") }

                val doNotStopSessionOptions = listOf("Yes", "No")
                val doNotStopSession = remember { mutableStateOf("") }

                val maxConnectorOptions = listOf("1", "2")
                val maxConnector = remember { mutableStateOf("") }

                var expandedDoNotStop = remember { mutableStateOf(false) }
                var expandedMaxConnector = remember { mutableStateOf(false) }
                val connectorType = remember { mutableStateOf("") }
                val connectorMode = remember { mutableStateOf("") }
                val earthFaultCheckedButton = remember { mutableStateOf(true) }
                val underVoltageLimit = remember { mutableStateOf("") }
                val overVoltageLimit = remember { mutableStateOf("") }
                val underCurrentLimit = remember { mutableStateOf("") }
                val overCurrentLimit = remember { mutableStateOf("") }
                val underCurrentTimeLimit = remember { mutableStateOf("") }
                val maxCurrentTimeLimit = remember { mutableStateOf("") }
                val defaultZone = remember { java.util.TimeZone.getDefault().id }
                val timeZone = remember { mutableStateOf(defaultZone) }




                OutlinedTextField(
                    value = modelName.value,
                    onValueChange = { modelName.value = it },
                    label = { Text("Model Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = serialNumber.value,
                    onValueChange = { serialNumber.value = it },
                    label = { Text("Serial No") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = vendor.value,
                    onValueChange = { vendor.value = it },
                    label = { Text("Vendor") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = websocketUrl.value,
                    onValueChange = { websocketUrl.value = it },
                    label = { Text("Websocket URL") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = authorizationToken.value,
                    onValueChange = { authorizationToken.value = it },
                    label = { Text("Authorization Token") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = electricityUnitPrice.value,
                    onValueChange = { electricityUnitPrice.value = it },
                    label = { Text("Electricity Unit Price") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = temperature.value,
                    onValueChange = { temperature.value = it },
                    label = { Text("Temperature (°C)") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = rfidTagLength.value,
                    onValueChange = { rfidTagLength.value = it },
                    label = { Text("RFID Tag Length") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Do Not Stop Session Dropdown
                ExposedDropdownMenuBox(
                    expanded = expandedDoNotStop.value,
                    onExpandedChange = { expandedDoNotStop.value = !expandedDoNotStop.value }
                ) {
                    OutlinedTextField(
                        value = doNotStopSession.value,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Do not stop session") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDoNotStop.value)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = expandedDoNotStop.value,
                        onDismissRequest = { expandedDoNotStop.value = false }
                    ) {
                        doNotStopSessionOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    doNotStopSession.value = option
                                    expandedDoNotStop.value = false
                                }
                            )
                        }
                    }
                }

                // Select Max Connector Dropdown
                ExposedDropdownMenuBox(
                    expanded = expandedMaxConnector.value,
                    onExpandedChange = { expandedMaxConnector.value = !expandedMaxConnector.value }
                ) {
                    OutlinedTextField(
                        value = maxConnector.value,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Select Max Connector") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedMaxConnector.value)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = expandedMaxConnector.value,
                        onDismissRequest = { expandedMaxConnector.value = false }
                    ) {
                        maxConnectorOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    maxConnector.value = option
                                    expandedMaxConnector.value = false
                                }
                            )
                        }
                    }
                }
                Text("Connector 1", modifier = Modifier.padding(start = 10.dp, end = 10.dp))
                OutlinedTextField(
                    value = connectorType.value,
                    onValueChange = { connectorType.value = it },
                    label = { Text("Type") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = connectorMode.value,
                    onValueChange = { connectorMode.value = it },
                    label = { Text("Type") },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(horizontalArrangement = Arrangement.Absolute.SpaceBetween) {
                    Text("Earth Fault", modifier = Modifier.padding(10.dp).align(Alignment.CenterVertically))
                    Switch(
                        checked = earthFaultCheckedButton.value,
                        onCheckedChange = { earthFaultCheckedButton.value = it },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }

                Text(
                    "Limit Configuration",
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 6.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ) {
                    OutlinedTextField(
                        value = underVoltageLimit.value,
                        onValueChange = { underVoltageLimit.value = it },
                        label = { Text("Under Voltage Limit", fontSize = 12.sp) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = overVoltageLimit.value,
                        onValueChange = { overVoltageLimit.value = it },
                        label = { Text("Over Voltage Limit", fontSize = 12.sp) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                        singleLine = true
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ) {
                    OutlinedTextField(
                        value = underCurrentLimit.value,
                        onValueChange = { underCurrentLimit.value = it },
                        label = { Text("Under Voltage Limit", fontSize = 12.sp) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = overCurrentLimit.value,
                        onValueChange = { overCurrentLimit.value = it },
                        label = { Text("Over Voltage Limit", fontSize = 12.sp) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp), // 👈 reduced height
                        textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                        singleLine = true
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ) {
                    OutlinedTextField(
                        value = underCurrentTimeLimit.value,
                        onValueChange = { underCurrentTimeLimit.value = it },
                        label = { Text("Under Voltage Limit", fontSize = 12.sp) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = maxCurrentTimeLimit.value,
                        onValueChange = { maxCurrentTimeLimit.value = it },
                        label = { Text("Over Voltage Limit", fontSize = 12.sp) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                        singleLine = true
                    )
                }
                OutlinedTextField(
                    value = timeZone.value,
                    onValueChange = { timeZone.value = it },
                    label = { Text("Time Zone") },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Skip Button
                    OutlinedButton(
                        onClick = { /* TODO: Handle Skip */ },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 6.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Skip")
                    }

                    // Save & Next Button
                    Button(
                        onClick = {   navController.navigate(NavigationRoute.RFID) {
                            popUpTo(NavigationRoute.CHARGER_POINT_CONFIG) { inclusive = true }
                        } },
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 6.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Save & Next")
                    }
                }

            }
        }
    }
}