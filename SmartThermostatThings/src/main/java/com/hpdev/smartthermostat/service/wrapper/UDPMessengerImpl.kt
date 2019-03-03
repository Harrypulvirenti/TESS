package com.hpdev.smartthermostat.service.wrapper

import com.hpdev.sdk.extensions.trimToString
import com.hpdev.sdk.logging.SmartLogger
import com.hpdev.smartthermostatcore.network.ObjectParser
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class UDPMessengerImpl(private val objectParser: ObjectParser) : UDPMessenger {

    override val job = Job()
    private val socket = DatagramSocket()
    private var ip: String? = null

    override fun setDestinationIP(ip: String) {
        this.ip = ip
    }

    override fun <T : Any> sendMessage(message: T, port: Int) {
        if (ip != null) {
            val messageByteArray = objectParser.toJSONBytes(message)
            val data = DatagramPacket(
                messageByteArray,
                messageByteArray.size,
                InetAddress.getByName(ip),
                port
            )


            launch(IO) {

                val buffer = ByteArray(5000)
                val receiver = DatagramPacket(buffer, buffer.size)
                socket.soTimeout = 1000
                socket.send(data)
                socket.receive(receiver)
                SmartLogger.d(buffer.trimToString())
            }
        } else {
            throw IOException("UDPMessenger Exception: the IP value wasn't settled yet")
        }
    }
}