package com.hpdev.smartthermostat.service.wrapper

import com.hpdev.architecture.sdk.extensions.trimToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import java.net.DatagramPacket
import java.net.InetAddress
import java.net.MulticastSocket

private const val BUFFER_SIZE = 2500

class MulticastReceiverImpl : MulticastReceiver {

    private lateinit var socket: MulticastSocket
    private lateinit var receiverDatagram: DatagramPacket
    private val receiverBuffer: ByteArray = ByteArray(BUFFER_SIZE)

    override suspend fun initSocket(
        groupIPAddress: String,
        port: Int,
        receivePort: Int,
        receiver: suspend (String) -> Unit
    ) {
        val groupAddress = InetAddress.getByName(groupIPAddress)
        socket = MulticastSocket(port)
        receiverDatagram = DatagramPacket(receiverBuffer, receiverBuffer.size, groupAddress, receivePort)

        withContext(Dispatchers.IO) {
            socket.joinGroup(groupAddress)
            while (isActive) {
                socket.receive(receiverDatagram)
                receiverBuffer.trimToString()?.let {
                    receiver(it)
                }
            }
        }
    }
}
