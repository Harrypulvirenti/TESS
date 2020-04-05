package com.tess.things.network

import com.tess.core.extensions.consume
import com.tess.extensions.kotlin.trimToString
import com.tess.things.models.IP
import java.net.DatagramPacket
import java.net.MulticastSocket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext

private const val BUFFER_SIZE = 2500

class MulticastReceiverImpl : MulticastReceiver {

    private lateinit var socket: MulticastSocket
    private lateinit var receiverDatagram: DatagramPacket

    override suspend fun initSocket(
        groupIPAddress: IP,
        port: Int,
        receivePort: Int,
        receiver: suspend (String) -> Unit
    ) {

        groupIPAddress.asInetAddress().consume { ip ->

            socket = MulticastSocket(port)
            receiverDatagram = DatagramPacket(ByteArray(BUFFER_SIZE), BUFFER_SIZE, ip, receivePort)

            withContext(Dispatchers.IO) {
                socket.joinGroup(ip)
                while (isActive) {
                    socket.receive(receiverDatagram)
                    receiverDatagram.data.trimToString()?.let {
                        receiver(it)
                    }
                }
            }
        }
    }
}
