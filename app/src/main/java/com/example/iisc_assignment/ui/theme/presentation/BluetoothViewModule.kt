package com.example.iisc_assignment.ui.theme.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iisc_assignment.ui.theme.domain.BluetoothController
import com.example.iisc_assignment.ui.theme.domain.BluetoothDevice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlinx.coroutines.flow.*


// Define UI state
data class BluetoothUiState(
    val scannedDevices: List<BluetoothDevice> = emptyList(),
    val pairedDevices: List<BluetoothDevice> = emptyList()
)

@HiltViewModel
class BluetoothViewModel @Inject constructor(
    private val bluetoothController: BluetoothController
) : ViewModel() {

    // Internal mutable state
    private val _uiState = MutableStateFlow(BluetoothUiStates())

    // Combine scannedDevices and pairedDevices flows from the controller
    val state: StateFlow<BluetoothUiStates> = combine(
        bluetoothController.scannedDevices,   // Flow<List<BluetoothDevice>>
        bluetoothController.pairedDevices     // Flow<List<BluetoothDevice>>
    ) { scanned, paired ->
        BluetoothUiStates(
            scannedDevices = scanned,
            pairedDevices = paired
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = _uiState.value
    )

    // Start scanning
    fun startScan() {
        bluetoothController.startDiscovery()
    }

    // Stop scanning
    fun stopScan() {
        bluetoothController.stopDiscovery()
    }
}
