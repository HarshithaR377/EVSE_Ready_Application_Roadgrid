package com.example.iisc_assignment

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.compose.material3.*
import androidx.compose.runtime.*
import java.util.UUID
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import android.bluetooth.*
import androidx.compose.ui.platform.LocalContext


private val SERVICE_UUID = UUID.fromString("12345678-1234-1234-1234-1234567890ab")
private val WIFI_CRED_UUID = UUID.fromString("12345678-4321-4321-4321-abcdef123456")
private val WIFI_STATUS_UUID = UUID.fromString("87654321-4321-4321-4321-abcdef654321")
private val CCC_DESCRIPTOR_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")

class MainActivity : ComponentActivity() {

    private lateinit var bluetoothAdapter: BluetoothAdapter
    private val handler = Handler(Looper.getMainLooper())
    private val scanResults = mutableStateListOf<BluetoothDevice>()
    private var bluetoothGatt: BluetoothGatt? = null
    private var wifiCharacteristic: BluetoothGattCharacteristic? = null
    private var wifiStatusCharacteristic: BluetoothGattCharacteristic? = null

    companion object {
        private const val SCAN_PERIOD: Long = 10000
        val wifiStatusState = mutableStateOf("")
    }

    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            runOnUiThread {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    Toast.makeText(this@MainActivity, "Connected to ${gatt.device.name}", Toast.LENGTH_SHORT).show()
                    gatt.discoverServices()
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    Toast.makeText(this@MainActivity, "Disconnected", Toast.LENGTH_SHORT).show()
                    wifiStatusState.value = ""
                }
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            val service = gatt.getService(SERVICE_UUID)
            wifiCharacteristic = service?.getCharacteristic(WIFI_CRED_UUID)
            wifiStatusCharacteristic = service?.getCharacteristic(WIFI_STATUS_UUID)

            // Enable notifications
            wifiStatusCharacteristic?.let { characteristic ->
                gatt.setCharacteristicNotification(characteristic, true)
                val descriptor = characteristic.getDescriptor(CCC_DESCRIPTOR_UUID)
                descriptor?.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                gatt.writeDescriptor(descriptor)
                Log.d("BLE", "✅ Wi-Fi status notifications enabled")
            }
        }

        override fun onCharacteristicChanged(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic) {
            if (characteristic.uuid == WIFI_STATUS_UUID) {
                val status = characteristic.getStringValue(0)
                Log.d("BLE", "📶 Wi-Fi Status: $status")
                runOnUiThread { wifiStatusState.value = status }
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { perms ->
            val granted = perms.values.all { it }
            if (granted) startScan()
            else Toast.makeText(this, "Permissions required", Toast.LENGTH_SHORT).show()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bluetoothAdapter = (getSystemService(BLUETOOTH_SERVICE) as BluetoothManager).adapter

        setContent {
            var selectedDevice by remember { mutableStateOf<BluetoothDevice?>(null) }

            if (selectedDevice == null) {
                DeviceListScreen(
                    devices = scanResults,
                    onDeviceClick = { device ->
                        selectedDevice = device
                        connectToDevice(device)
                    },
                    onScanClick = { checkPermissionsAndScan() }
                )
            } else {
                WifiConfigScreen(
                    wifiStatus = wifiStatusState.value,
                    onSend = { ssid, pass -> sendWifiConfig(ssid, pass) }
                )
            }
        }
    }

    private fun checkPermissionsAndScan() {
        val permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT
        )
        val missing = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }
        if (missing.isNotEmpty()) {
            requestPermissionLauncher.launch(missing.toTypedArray())
        } else {
            startScan()
        }
    }

    @SuppressLint("MissingPermission")
    private fun startScan() {
        val scanner = bluetoothAdapter.bluetoothLeScanner
        scanResults.clear()
        handler.postDelayed({ scanner.stopScan(scanCallback) }, SCAN_PERIOD)
        scanner.startScan(scanCallback)
    }

    private val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            if (!scanResults.contains(result.device)) scanResults.add(result.device)
        }
    }

    @SuppressLint("MissingPermission")
    private fun connectToDevice(device: BluetoothDevice) {
        bluetoothAdapter.bluetoothLeScanner?.stopScan(scanCallback)
        bluetoothGatt = device.connectGatt(this, false, gattCallback, BluetoothDevice.TRANSPORT_LE)
        Toast.makeText(this, "Connecting to ${device.name ?: "ESP32"}", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("MissingPermission")
    private fun sendWifiConfig(ssid: String, password: String) {
        val data = "$ssid,$password"
        wifiCharacteristic?.let { characteristic ->
            characteristic.value = data.toByteArray()
            characteristic.writeType = BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT
            bluetoothGatt?.writeCharacteristic(characteristic)
            Toast.makeText(this, "Wi-Fi credentials sent", Toast.LENGTH_SHORT).show()
        } ?: run {
            Toast.makeText(this, "Wi-Fi characteristic not found", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun DeviceListScreen(
    devices: List<BluetoothDevice>,
    onDeviceClick: (BluetoothDevice) -> Unit,
    onScanClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Available Devices", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Button(onClick = onScanClick, modifier = Modifier.fillMaxWidth()) { Text("Scan for Devices") }
        Spacer(Modifier.height(16.dp))
        LazyColumn {
            items(devices) { device ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .clickable { onDeviceClick(device) }
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(device.name ?: "Unnamed Device", fontWeight = FontWeight.Bold)
                        Text(device.address, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

@Composable
fun WifiConfigScreen(wifiStatus: String, onSend: (String, String) -> Unit) {
    var ssid by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Wi-Fi Configuration", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = ssid,
            onValueChange = { ssid = it },
            label = { Text("Wi-Fi SSID") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Wi-Fi Password") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                if (ssid.isNotBlank() && password.isNotBlank()) {
                    onSend(ssid, password)
                    Toast.makeText(context, "Wi-Fi credentials sent", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Enter SSID and Password", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Send Wi-Fi Config")
        }

        Spacer(Modifier.height(20.dp))

        if (wifiStatus.isNotEmpty()) {
            Text("Wi-Fi Status: $wifiStatus", style = MaterialTheme.typography.bodyLarge)
        }
        Log.d("WiFiStatus", "Current Wi-Fi Status: $wifiStatus")
    }
}


