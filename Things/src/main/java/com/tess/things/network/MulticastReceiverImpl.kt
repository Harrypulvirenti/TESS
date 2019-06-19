package com.tess.things.network

import com.tess.architecture.sdk.extensions.trimToString
import com.tess.things.models.IP
import com.tess.core.extensions.consume
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import java.net.DatagramPacket
import java.net.MulticastSocket

private const val BUFFER_SIZE = 2500

class MulticastReceiverImpl : MulticastReceiver {

    private lateinit var socket: MulticastSocket
    private lateinit var receiverDatagram: DatagramPacket
    private val receiverBuffer: ByteArray = ByteArray(BUFFER_SIZE)

    override suspend fun initSocket(
        groupIPAddress: IP,
        port: Int,
        receivePort: Int,
        receiver: suspend (String) -> Unit
    ) {

        groupIPAddress.asInetAddress().consume { ip ->

            socket = MulticastSocket(port)
            receiverDatagram = DatagramPacket(receiverBuffer, receiverBuffer.size, ip, receivePort)

            withContext(Dispatchers.IO) {
                socket.joinGroup(ip)
                while (isActive) {
                    socket.receive(receiverDatagram)
                    receiverBuffer.trimToString()?.let {
                        receiver(it)
                    }
                }
            }
        }
    }
}
