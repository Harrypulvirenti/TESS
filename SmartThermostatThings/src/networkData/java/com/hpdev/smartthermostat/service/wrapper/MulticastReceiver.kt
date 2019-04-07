package com.hpdev.smartthermostat.service.wrapper

import com.hpdev.architecture.sdk.extensions.trimToString
import com.hpdev.architecture.sdk.interfaces.CoroutineHandler
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
) : NetworkReceiver, CoroutineHandler {

    private val socket: MulticastSocket = MulticastSocket(port)
    private val groupAddress: InetAddress = InetAddress.getByName(groupIPAddress)
    private val receiverBuffer: ByteArray = ByteArray(bufferSize)
    private val receiverDatagram: DatagramPacket =
        DatagramPacket(receiverBuffer, receiverBuffer.size, groupAddress, receivePort)

    override val job = Job()

    init {
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
}