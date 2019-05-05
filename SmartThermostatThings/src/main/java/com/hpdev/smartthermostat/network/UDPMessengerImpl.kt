package com.hpdev.smartthermostat.network

import com.hpdev.architecture.sdk.extensions.trimToString
import com.hpdev.architecture.sdk.interfaces.CoroutineHandler
import com.hpdev.architecture.sdk.utils.SmartLogger
import com.hpdev.smartthermostatcore.extensions.consume
import com.hpdev.smartthermostatcore.network.ObjectParser
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class UDPMessengerImpl(private val objectParser: ObjectParser) : UDPMessenger, CoroutineHandler {

    override val job = Job()
    private val socket = DatagramSocket()
    private var ip: String? = null

    override fun setDestinationIP(ip: String) {
        this.ip = ip
    }

    override fun <T : Any> sendMessage(message: T, port: Int) {
        if (ip != null) {
            var data: DatagramPacket? = null
            objectParser.toJSONBytes(message).consume {
                data = DatagramPacket(
                    it,
                    it.size,
                    InetAddress.getByName(ip),
                    port
                )
            }

            data?.let {

                launch(IO) {

                    val buffer = ByteArray(5000)
                    val receiver = DatagramPacket(buffer, buffer.size)
                    socket.soTimeout = 1000
                    socket.send(data)
                    withTimeoutOrNull(1000L) {
                        socket.receive(receiver)
                    }
                    SmartLogger.d(buffer.trimToString())
                }
            }
        } else {
            throw IOException("UDPMessenger Exception: the IP value wasn't settled yet")
        }
    }
}