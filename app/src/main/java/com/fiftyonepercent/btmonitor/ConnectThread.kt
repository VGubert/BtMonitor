package com.fiftyonepercent.btmonitor

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.util.*


@SuppressLint("MissingPermission")
class ConnectThread(device: BluetoothDevice) : Thread() {
    val uuid = "00001101-0000-1000-8000-00805F9B34FB" //код соединения
    var mSocket: BluetoothSocket? = null
    lateinit var rThread: ReceiveThread

    init {
        try{
            mSocket = device.createRfcommSocketToServiceRecord(UUID.fromString(uuid)) //создаться сокет и через него можно производить подключения
        } catch (i: IOException) {

        }
    }
    override fun run() { //это функция будет запускаться на второстепенном потоке
        try {
            Log.d("MyLog", "Connecting...")
            mSocket?.connect()
            Log.d("MyLog", "Connected")
            rThread = ReceiveThread(mSocket!!)
            rThread.start()
        } catch (i: IOException) {
            Log.d("MyLog", "Can not connect to device")
            closeConnection()

        }
    }

    fun closeConnection() { //закрытие соединения
        try {
            mSocket?.close()
        } catch (i: IOException) {

        }
    }

}