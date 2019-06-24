package com.tess.things.network

import arrow.core.Either
import arrow.core.Try
import arrow.core.extensions.either.monad.flatten
import arrow.core.flatMap
import arrow.core.toOption
import com.tess.architecture.sdk.extensions.trimToString
import com.tess.core.models.GenericError
import com.tess.core.models.NetworkError
import com.tess.core.network.ObjectParser
import com.tess.things.models.IP
import com.tess.things.models.TimeoutError
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

class UDPMessengerImpl(
    private val objectParser: ObjectParser,
    private val socket: DatagramSocket = DatagramSocket()
) : UDPMessenger {

    init {
        socket.soTimeout = TIMEOUT
    }

    override suspend fun <T : Any> sendMessage(ip: IP, port: Int, message: T): Either<GenericError, Unit> =
        ip.asInetAddress()
            .parseMessage(port, message)
            .sendMessage()

    override suspend fun <T : Any, R : Any> sendAndReceiveMessage(
        ip: IP,
        port: Int,
        message: T,
        response: KClass<R>
    ): Either<GenericError, R> =
        sendMessage(ip, port, message)
            .receiveMessage()
            .parseMessage(response)

    private suspend fun sendMessage(data: DatagramPacket): Either<GenericError, Unit> =
        withContext(IO) {
            Try {
                socket.send(data)
            }.toEither { NetworkError("Network Error: " + it.message, it) }
        }

    private suspend fun <T : Any> Either<GenericError, InetAddress>.parseMessage(
        port: Int,
        message: T
    ): Either<GenericError, DatagramPacket> =
        withContext(Default) {
            flatMap { ip ->
                objectParser.toJSONBytes(message).map { bytesMessage ->
                    DatagramPacket(
                        bytesMessage,
                        bytesMessage.size,
                        ip,
                        port
                    )
                }
            }
        }

    private suspend fun Either<GenericError, DatagramPacket>.sendMessage(): Either<GenericError, Unit> =
        flatMap { data ->
            sendMessage(data)
        }

    private suspend fun Either<GenericError, Unit>.receiveMessage(): Either<GenericError, String> =
        withContext(IO) {
            flatMap {
                Try {
                    val receiver = DatagramPacket(ByteArray(BUFFER_SIZE), BUFFER_SIZE)
                    withTimeoutOrNull(TIMEOUT.toLong()) {
                        socket.receive(receiver)
                        receiver.data.trimToString()
                    }.toOption().toEither { TimeoutError("Receiver Timeout Error") }

                }.toEither { NetworkError("Network Error: " + it.message, it) }
            }.flatten()
        }

    private suspend fun <R : Any> Either<GenericError, String>.parseMessage(response: KClass<R>): Either<GenericError, R> =
        withContext(Default) {
            flatMap { message ->
                objectParser.parseJson(message, response)
            }
        }
}

suspend inline fun <T : Any, reified R : Any> UDPMessenger.sendAndReceiveMessage(
    ip: IP,
    port: Int,
    message: T
): Either<GenericError, R> = this.sendAndReceiveMessage(ip, port, message, R::class)