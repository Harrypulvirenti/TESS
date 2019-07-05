package com.tess.things.network

import arrow.core.Either
import arrow.core.orNull
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.tess.core.models.NetworkError
import com.tess.core.models.ParsingError
import com.tess.core.network.ObjectParser
import com.tess.core.network.parseJson
import com.tess.things.models.MalformedIPError
import com.tess.things.models.TimeoutError
import com.tess.things.models.asIP
import io.mockk.CapturingSlot
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.net.DatagramPacket
import java.net.DatagramSocket
import kotlin.reflect.KClass

class UDPMessengerImplTest {

    private val objectParser = mockk<ObjectParser>()
    private val socket = mockk<DatagramSocket>(relaxed = true, relaxUnitFun = true)

    private val sut = UDPMessengerImpl(objectParser, socket)

    @Test
    fun `Should send a parsed message inside the socket`() {

        val ip = "255.255.255.255".asIP()
        val obj = TestJsonClass()
        val port = 5
        val objByte = ByteArray(3).apply {
            this[0] = 1
            this[1] = 0
        }

        every { objectParser.toJSONBytes(any<TestJsonClass>()) } returns Either.right(objByte)


        runBlocking {

            sut.sendMessage(ip, port, obj)

            verify {
                objectParser.toJSONBytes(obj)
                socket.send(withArg {

                    with(actual as DatagramPacket) {
                        assertEquals(objByte, data)
                        assertEquals(objByte.size, length)
                        assertEquals(ip.value, address.hostAddress)
                        assertEquals(port, port)
                    }
                })
            }
        }
    }

    @Test
    fun `Should return left Either of MalformedIPError on sendMessage`() {

        val ip = "not valid".asIP()
        val obj = TestJsonClass()
        val port = 5

        runBlocking {

            val result = sut.sendMessage(ip, port, obj)

            verify(exactly = 0) {
                objectParser.toJSONBytes(any<TestJsonClass>())
                socket.send(any())
            }

            result.fold(
                {
                    assertTrue(it is MalformedIPError)
                },
                {
                    fail()
                })
        }
    }

    @Test
    fun `Should return left Either of ParsingError on sendMessage`() {

        val ip = "255.255.255.255".asIP()
        val obj = TestJsonClass()
        val port = 5
        val error = ParsingError("error", null)

        every { objectParser.toJSONBytes(any<TestJsonClass>()) } returns Either.left(error)

        runBlocking {

            val result = sut.sendMessage(ip, port, obj)

            verify { objectParser.toJSONBytes(obj) }

            verify(exactly = 0) { socket.send(any()) }

            result.fold(
                {
                    assertEquals(error, it)
                },
                {
                    fail()
                })
        }
    }

    @Test
    fun `Should return left Either of NetworkError on sendMessage`() {

        val ip = "255.255.255.255".asIP()
        val obj = TestJsonClass()
        val port = 5
        val objByte = ByteArray(3).apply {
            this[0] = 1
            this[1] = 0
        }
        val throwable = Throwable("message")
        val error = NetworkError("Network Error: message", throwable)

        every { objectParser.toJSONBytes(any<TestJsonClass>()) } returns Either.right(objByte)
        every { socket.send(any()) } throws throwable

        runBlocking {

            val result = sut.sendMessage(ip, port, obj)

            verify {
                objectParser.toJSONBytes(obj)
                socket.send(withArg {

                    with(actual as DatagramPacket) {
                        assertEquals(objByte, data)
                        assertEquals(objByte.size, length)
                        assertEquals(ip.value, address.hostAddress)
                        assertEquals(port, port)
                    }
                })
            }

            result.fold(
                {
                    assertEquals(error, it)
                },
                {
                    fail()
                })
        }
    }

    @Test
    fun `Should send a parsed message and receive response inside the socket`() {

        val ip = "255.255.255.255".asIP()
        val obj = TestJsonClass()
        val port = 5
        val objByte = ByteArray(3).apply {
            this[0] = 1
            this[1] = 0
        }
        val receivedString = "{\"string\":\"string\"}"
        val bufferSlot = CapturingSlot<DatagramPacket>()
        val receivedObj = TestJsonClass("string2", 4)

        every { objectParser.toJSONBytes(any<TestJsonClass>()) } returns Either.right(objByte)
        every { socket.receive(capture(bufferSlot)) } answers {
            bufferSlot.captured.data = receivedString.toByteArray()
        }

        every { objectParser.parseJson<TestJsonClass>(any<String>()) } returns Either.right(receivedObj)


        runBlocking {

            val result = sut.sendAndReceiveMessage<TestJsonClass, TestJsonClass>(ip, port, obj).orNull()

            verify {
                objectParser.toJSONBytes(obj)
                socket.send(withArg {

                    with(actual as DatagramPacket) {
                        assertEquals(objByte, data)
                        assertEquals(objByte.size, length)
                        assertEquals(ip.value, address.hostAddress)
                        assertEquals(port, port)
                    }
                })
                socket.receive(any())
                objectParser.parseJson(receivedString, TestJsonClass::class)
            }

            assertEquals(receivedObj, result)

        }
    }

    @Test
    fun `Should return left Either of MalformedIPError on sendAndReceiveMessage`() {

        val ip = "not valid".asIP()
        val obj = TestJsonClass()
        val port = 5

        runBlocking {

            val result = sut.sendAndReceiveMessage<TestJsonClass, TestJsonClass>(ip, port, obj)

            verify(exactly = 0) {
                objectParser.toJSONBytes(any<TestJsonClass>())
                socket.send(any())
                socket.receive(any())
                objectParser.parseJson(any<String>(), any<KClass<Any>>())
            }

            result.fold(
                {
                    assertTrue(it is MalformedIPError)
                },
                {
                    fail()
                })
        }
    }

    @Test
    fun `Should return left Either of ParsingError on sendAndReceiveMessage`() {

        val ip = "255.255.255.255".asIP()
        val obj = TestJsonClass()
        val port = 5
        val error = ParsingError("error", null)

        every { objectParser.toJSONBytes(any<TestJsonClass>()) } returns Either.left(error)

        runBlocking {

            val result = sut.sendAndReceiveMessage<TestJsonClass, TestJsonClass>(ip, port, obj)

            verify { objectParser.toJSONBytes(obj) }

            verify(exactly = 0) {
                socket.send(any())
                socket.receive(any())
                objectParser.parseJson(any<String>(), any<KClass<Any>>())
            }

            result.fold(
                {
                    assertEquals(error, it)
                },
                {
                    fail()
                })
        }
    }

    @Test
    fun `Should return left Either of NetworkError on sendAndReceiveMessage`() {

        val ip = "255.255.255.255".asIP()
        val obj = TestJsonClass()
        val port = 5
        val objByte = ByteArray(3).apply {
            this[0] = 1
            this[1] = 0
        }
        val throwable = Throwable("message")
        val error = NetworkError("Network Errorrrr: message", throwable)

        every { objectParser.toJSONBytes(any<TestJsonClass>()) } returns Either.right(objByte)
        every { socket.send(any()) } throws throwable

        runBlocking {

            val result = sut.sendAndReceiveMessage<TestJsonClass, TestJsonClass>(ip, port, obj)

            verify {
                objectParser.toJSONBytes(obj)
                socket.send(withArg {

                    with(actual as DatagramPacket) {
                        assertEquals(objByte, data)
                        assertEquals(objByte.size, length)
                        assertEquals(ip.value, address.hostAddress)
                        assertEquals(port, port)
                    }
                })
            }

            verify(exactly = 0) {
                socket.receive(any())
                objectParser.parseJson(any<String>(), any<KClass<Any>>())
            }

            result.fold(
                {
                    assertEquals(error, it)
                },
                {
                    fail()
                })
        }
    }

    @Test
    fun `Should return left Either of TimeoutError on sendAndReceiveMessage`() {

        val ip = "255.255.255.255".asIP()
        val obj = TestJsonClass()
        val port = 5
        val objByte = ByteArray(3).apply {
            this[0] = 1
            this[1] = 0
        }
        val error = TimeoutError("Receiver Timeout Error")

        every { objectParser.toJSONBytes(any<TestJsonClass>()) } returns Either.right(objByte)

        runBlocking {

            val result = sut.sendAndReceiveMessage<TestJsonClass, TestJsonClass>(ip, port, obj)

            verify {
                objectParser.toJSONBytes(obj)
                socket.send(withArg {

                    with(actual as DatagramPacket) {
                        assertEquals(objByte, data)
                        assertEquals(objByte.size, length)
                        assertEquals(ip.value, address.hostAddress)
                        assertEquals(port, port)
                    }
                })
                socket.receive(any())
            }

            verify(exactly = 0) {
                objectParser.parseJson(any<String>(), any<KClass<Any>>())
            }

            result.fold(
                {
                    assertEquals(error, it)
                },
                {
                    fail()
                })
        }
    }

    @Test
    fun `Should return left Either of NetworkError on sendAndReceiveMessage of receive`() {

        val ip = "255.255.255.255".asIP()
        val obj = TestJsonClass()
        val port = 5
        val objByte = ByteArray(3).apply {
            this[0] = 1
            this[1] = 0
        }
        val throwable = Throwable("message")
        val error = NetworkError("Network Error: message", throwable)

        every { objectParser.toJSONBytes(any<TestJsonClass>()) } returns Either.right(objByte)
        every { socket.receive(any()) } throws throwable


        runBlocking {

            val result = sut.sendAndReceiveMessage<TestJsonClass, TestJsonClass>(ip, port, obj)

            verify {
                objectParser.toJSONBytes(obj)
                socket.send(withArg {

                    with(actual as DatagramPacket) {
                        assertEquals(objByte, data)
                        assertEquals(objByte.size, length)
                        assertEquals(ip.value, address.hostAddress)
                        assertEquals(port, port)
                    }
                })
                socket.receive(any())
            }

            verify(exactly = 0) {
                objectParser.parseJson(any<String>(), any<KClass<Any>>())
            }

            result.fold(
                {
                    assertEquals(error, it)
                },
                {
                    fail()
                })
        }
    }

    @Test
    fun `Should return left Either of ParsingError on sendAndReceiveMessage of receive`() {

        val ip = "255.255.255.255".asIP()
        val obj = TestJsonClass()
        val port = 5
        val objByte = ByteArray(3).apply {
            this[0] = 1
            this[1] = 0
        }
        val receivedString = "{\"string\":\"string\"}"
        val bufferSlot = CapturingSlot<DatagramPacket>()

        val error = ParsingError("error", null)


        every { objectParser.toJSONBytes(any<TestJsonClass>()) } returns Either.right(objByte)
        every { socket.receive(capture(bufferSlot)) } answers {
            bufferSlot.captured.data = receivedString.toByteArray()
        }
        every { objectParser.parseJson<TestJsonClass>(any<String>()) } returns Either.left(error)


        runBlocking {

            val result = sut.sendAndReceiveMessage<TestJsonClass, TestJsonClass>(ip, port, obj)

            verify {
                objectParser.toJSONBytes(obj)
                socket.send(withArg {

                    with(actual as DatagramPacket) {
                        assertEquals(objByte, data)
                        assertEquals(objByte.size, length)
                        assertEquals(ip.value, address.hostAddress)
                        assertEquals(port, port)
                    }
                })
                socket.receive(any())
                objectParser.parseJson(receivedString, TestJsonClass::class)
            }

            result.fold(
                {
                    assertEquals(error, it)
                },
                {
                    fail()
                })
        }
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
private data class TestJsonClass(
    @JsonProperty("string") val string: String = "string",
    @JsonProperty("int") val int: Int = 2
)