package com.hpdev.smartthermostat.network

import arrow.core.Either
import arrow.core.Try
import arrow.core.Tuple2
import arrow.core.extensions.either.functor.tupleRight
import arrow.core.extensions.either.monad.flatten
import arrow.core.flatMap
import arrow.core.toOption
import com.hpdev.architecture.sdk.extensions.trimToString
import com.hpdev.smartthermostat.models.IP
import com.hpdev.smartthermostat.models.MessageParsingError
import com.hpdev.smartthermostat.models.TimeoutError
import com.hpdev.smartthermostatcore.models.ApplicativeError
import com.hpdev.smartthermostatcore.models.GenericError
import com.hpdev.smartthermostatcore.models.NetworkError
import com.hpdev.smartthermostatcore.network.ObjectParser
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import kotlin.reflect.KClass

private const val TIMEOUT = 2000
private const val BUFFER_SIZE = 5000

class UDPMessengerImpl(private val objectParser: ObjectParser) : UDPMessenger {

    private val socket = DatagramSocket()

    init {
        socket.soTimeout = TIMEOUT
    }

    override suspend fun <T : Any> sendMessage(ip: IP, port: Int, message: T): Either<NetworkError, Unit> =
        ip.asInetAddress()
            .parseMessage(port, message)
            .sendMessage()

    override suspend fun <T : Any, R : Any> sendAndReceiveMessage(
        ip: IP,
        port: Int,
        message: T,
        response: KClass<R>
    ): Either<NetworkError, R> =
        ip.asInetAddress()
            .parseMessage(port, message)
            .sendMessage()
            .receiveMessage()
            .parseMessage(response)

    private suspend fun sendMessage(data: DatagramPacket): Either<GenericError, Unit> =
        withContext(IO) {
            Try {
                socket.send(data)
            }.toEither { GenericError(it) }
        }

    private suspend fun <T : Any> Either<NetworkError, InetAddress>.parseMessage(
        port: Int,
        message: T
    ): Either<NetworkError, DatagramPacket> =
        withContext(Default) {
            tupleRight(objectParser.toJSONBytes(message))
                .flatMap { tuple: Tuple2<InetAddress, Either<ApplicativeError, ByteArray>> ->
                    tuple.b
                        .mapToParsingError()
                        .map { bytesMessage ->
                            DatagramPacket(
                                bytesMessage,
                                bytesMessage.size,
                                tuple.a,
                                port
                            )
                        }
                }
        }

    private suspend fun Either<NetworkError, DatagramPacket>.sendMessage(): Either<NetworkError, Unit> =
        this.flatMap { data ->
            sendMessage(data)
        }.mapLeft {
            it as GenericError
            NetworkError("Network Error: " + it.e.message)
        }

    private suspend fun Either<NetworkError, Unit>.receiveMessage(): Either<NetworkError, String> =
        withContext(IO) {
            map {
                Try {
                    val buffer = ByteArray(BUFFER_SIZE)
                    val receiver = DatagramPacket(buffer, buffer.size)
                    withTimeoutOrNull(TIMEOUT.toLong()) {
                        socket.receive(receiver)
                        buffer.trimToString()
                    }.toOption().toEither { TimeoutError("Receiver timeout") }
                }.toEither { NetworkError("Network Error: " + it.message) }
            }.flatten().flatten()
        }

    private suspend fun <R : Any> Either<NetworkError, String>.parseMessage(response: KClass<R>): Either<NetworkError, R> =
        withContext(Default) {
            map { message ->
                objectParser.parseJson(message, response)
                    .mapToParsingError()
            }.flatten()
        }

    private fun <T : Any> Either<ApplicativeError, T>.mapToParsingError(): Either<MessageParsingError, T> =
        this.mapLeft {
            it as GenericError
            MessageParsingError("Message Parsing Error: " + it.e.message)
        }
}

suspend inline fun <T : Any, reified R : Any> UDPMessenger.sendAndReceiveMessage(
    ip: IP,
    port: Int,
    message: T
): Either<NetworkError, R> = this.sendAndReceiveMessage(ip, port, message, R::class)