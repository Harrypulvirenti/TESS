package com.hpdev.smartthermostat.service.wrapper

import com.hpdev.sdk.extensions.trimToString
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.net.DatagramPacket
import java.net.InetAddress
import java.net.MulticastSocket

abstract class MulticastReceiver(
    groupIPAddress: String,
    port: Int,
    receivePort: Int = port,
    bufferSize: Int
) : NetworkReceiver {

    private val socket: MulticastSocket = MulticastSocket(port)
    private val groupAddress: InetAddress = InetAddress.getByName(groupIPAddress)
    private val receiverBuffer: ByteArray = ByteArray(bufferSize)
    private val receiverDatagram: DatagramPacket =
        DatagramPacket(receiverBuffer, receiverBuffer.size, groupAddress, receivePort)

    override val job = Job()

    override fun startReceiver() {
        launch(IO) {
            socket.joinGroup(groupAddress)
            while (isActive) {
                socket.receive(receiverDatagram)
                receiverBuffer.trimToString()?.let {
                    onMessageReceived(it)
                }
            }
        }
    }

    override fun stopReceiver() {
        socket.leaveGroup(groupAddress)
        job.cancel()
    }
}