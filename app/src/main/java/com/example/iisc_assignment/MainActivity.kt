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
import androidx.compose.runtime.*
import java.util.UUID
import androidx.core.content.ContextCompat
import android.bluetooth.*
import androidx.navigation.compose.rememberNavController
import com.example.iisc_assignment.ui.theme.navigation.AppNavigation
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController


// UUIDs for your ESP32 BLE service and characteristics
private val SERVICE_UUID = UUID.fromString("12345678-1234-1234-1234-1234567890ab")
private val WIFI_CRED_UUID = UUID.fromString("12345678-4321-4321-4321-abcdef123456")
private val WIFI_STATUS_UUID = UUID.fromString("87654321-4321-4321-4321-abcdef654321")
private val CCC_DESCRIPTOR_UUID =
    UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")

class MainActivity : ComponentActivity() {

    private lateinit var bluetoothAdapter: BluetoothAdapter
    private val handler = Handler(Looper.getMainLooper())
    private val scanResults = mutableStateListOf<BluetoothDevice>()
    private var bluetoothGatt: BluetoothGatt? = null
    private var wifiCharacteristic: BluetoothGattCharacteristic? = null
    private var wifiStatusCharacteristic: BluetoothGattCharacteristic? = null

    // Navigation controller we’ll assign later in setContent
    private lateinit var navControllerForCallbacks: NavHostController

    companion object {
        private const val SCAN_PERIOD: Long = 10000
        val wifiStatusState = mutableStateOf("")
    }

    // ✅ BLE GATT Callback
    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            runOnUiThread {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    Toast.makeText(
                        this@MainActivity,
                        "Connected to ${gatt.device.name}",
                        Toast.LENGTH_SHORT
                    ).show()
                    gatt.discoverServices()

                    // 👉 Navigate to Wi-Fi screen when connected
                    try {
                        navControllerForCallbacks.navigate("wifi") {
                            popUpTo("devices") { inclusive = false }
                        }
                    } catch (e: Exception) {
                        Log.w("MainActivity", "Navigation failed: ${e.message}")
                    }
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

            wifiStatusCharacteristic?.let { characteristic ->
                gatt.setCharacteristicNotification(characteristic, true)
                val descriptor = characteristic.getDescriptor(CCC_DESCRIPTOR_UUID)
                descriptor?.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                gatt.writeDescriptor(descriptor)
                Log.d("BLE", "Wi-Fi status notifications enabled")
            }
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic
        ) {
            if (characteristic.uuid == WIFI_STATUS_UUID) {
                val status = characteristic.getStringValue(0)
                Log.d("BLE", "📶 Wi-Fi Status: $status")
                runOnUiThread { wifiStatusState.value = status }
            }
        }
    }

    // ✅ Permission launcher
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
            val navController = rememberNavController()
            navControllerForCallbacks = navController // assign for callbacks
            AppNavigation(
                navController = navController,
                scanResults = scanResults,
                onDeviceClick = { device -> connectToDevice(device) },
                onScanClick = { checkPermissionsAndScan() },
                wifiStatus = wifiStatusState.value,
                onSend = { ssid, pass -> sendWifiConfig(ssid, pass) }
            )
        }
    }

    // ✅ Permission check before scanning
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

    // ✅ BLE Scan
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

    // ✅ Connect to device
    @SuppressLint("MissingPermission")
    private fun connectToDevice(device: BluetoothDevice) {
        bluetoothAdapter.bluetoothLeScanner?.stopScan(scanCallback)
        bluetoothGatt = device.connectGatt(this, false, gattCallback, BluetoothDevice.TRANSPORT_LE)
        Toast.makeText(this, "Connecting to ${device.name ?: "ESP32"}", Toast.LENGTH_SHORT).show()
    }

    // ✅ Send Wi-Fi credentials
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

