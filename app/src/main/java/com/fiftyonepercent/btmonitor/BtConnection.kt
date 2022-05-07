package com.fiftyonepercent.btmonitor

import android.bluetooth.BluetoothAdapter

class BtConnection(private val adapter: BluetoothAdapter) {
    lateinit var cThread: ConnectThread
    fun connect(mac: String) {
        if(adapter.isEnabled && mac.isNotEmpty()) { //если блютуз включен
            val device = adapter.getRemoteDevice(mac) //получаем по mac адресу устройство
            device.let {
                cThread = ConnectThread(it)
                cThread.start()

            }
        }
    }
    fun sendMessage(message: String) {
        cThread.rThread.sendMessage(message.toByteArray())
    }
}