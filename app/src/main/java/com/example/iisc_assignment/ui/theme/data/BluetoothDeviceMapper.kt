package com.example.iisc_assignment.ui.theme.data

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.example.iisc_assignment.ui.theme.domain.BluetoothDeviceDomain

@SuppressLint("MissingPermission")
fun BluetoothDevice.toBluetoothDeviceDomain(): BluetoothDeviceDomain {
    return BluetoothDeviceDomain(
        name = name,
        address = address
    )
}