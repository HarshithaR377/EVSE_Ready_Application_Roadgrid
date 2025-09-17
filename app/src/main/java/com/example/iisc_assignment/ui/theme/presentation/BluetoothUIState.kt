package com.example.iisc_assignment.ui.theme.presentation

import com.example.iisc_assignment.ui.theme.domain.BluetoothDevice


data class BluetoothUiStates(
    val scannedDevices: List<BluetoothDevice> = emptyList(),
    val pairedDevices: List<BluetoothDevice> = emptyList(),
)
