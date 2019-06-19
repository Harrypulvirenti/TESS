package com.tess.things.service.aqara

import arrow.core.Either.Companion.left
import arrow.core.Either.Companion.right
import com.tess.architecture.sdk.utils.SmartLogger
import com.tess.things.models.aqara.AqaraMessageData
import com.tess.things.models.aqara.AqaraNetMessage
import com.tess.things.network.MulticastReceiver
import com.tess.things.service.AqaraMessageReceiverImpl
import com.tess.core.models.ParsingError
import com.tess.core.network.ObjectParser
import com.tess.core.network.parseJson
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class AqaraMessageReceiverImplTest {

    private val objectParser = mockk<ObjectParser>()
    private val multicastReceiver = mockk<MulticastReceiver>(relaxed = true)

    private val sut = AqaraMessageReceiverImpl(multicastReceiver, objectParser)

    @Test
    fun `Should send parsed message inside the channel`() {

        val json = ""
        val jsonData = "\"temperature\":\"20\""
        val message = AqaraNetMessage(
            commandName = "command",
            model = "model",
            sid = "sid",
            dataJson = jsonData
        )
        val messageData = AqaraMessageData(
            temperature = "20",
            humidity = "10"
        )
        message.data = messageData

        runBlocking {

            val channel = sut.subscribeMessageReceiver()

            every { objectParser.parseJson<AqaraNetMessage>(any<String>()) } returns right(message)

            every { objectParser.parseJson<AqaraMessageData>(any<String>()) } returns right(messageData)

            sut.onNetworkMessageReceived(json)

            verify {
                objectParser.parseJson<AqaraNetMessage>(json)
                objectParser.parseJson<AqaraMessageData>(jsonData)
            }

            assertEquals(message, channel.receiveOrNull())
        }
    }

    @Test
    fun `Should log exception on parsing error`() {

        val json = "json"
        val error = ParsingError("Error", Exception("Exception"))
        mockkObject(SmartLogger.Companion)

        runBlocking {

            every { objectParser.parseJson<AqaraNetMessage>(any<String>()) } returns left(error)
            sut.onNetworkMessageReceived(json)

            verify {
                objectParser.parseJson<AqaraNetMessage>(json)
                SmartLogger.e(
                    errorMessage = error.message,
                    throwable = error.e
                )
            }
        }
    }

    @Test
    fun `Should parse data json and log error`() {

        val json = ""
        val jsonData = "\"temperature\":\"20\""
        val message = AqaraNetMessage(
            commandName = "command",
            model = "model",
            sid = "sid",
            dataJson = jsonData
        )
        val error = ParsingError("Error", Exception("Exception"))
        mockkObject(SmartLogger.Companion)

        runBlocking {

            val channel = sut.subscribeMessageReceiver()

            every { objectParser.parseJson<AqaraNetMessage>(any<String>()) } returns right(message)

            every { objectParser.parseJson<AqaraMessageData>(any<String>()) } returns left(error)

            sut.onNetworkMessageReceived(json)

            verify {
                objectParser.parseJson<AqaraNetMessage>(json)
                objectParser.parseJson<AqaraMessageData>(jsonData)
                SmartLogger.e(
                    errorMessage = error.message,
                    throwable = error.e
                )
            }

            assertEquals(message, channel.receiveOrNull())
        }
    }
}