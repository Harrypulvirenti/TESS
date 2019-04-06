package com.hpdev.smartthermostatcore.network

import com.hpdev.netmodels.aqara.AqaraNetMessage
import junit.framework.TestCase.fail
import org.junit.Test

class ObjectParserImplTest {

    private val sut = ObjectParserImpl()

    @Test
    fun testSuccessJsonParsing() {
        val json =
            "{\"cmd\":\"heartbeat\",\"model\":\"gateway\",\"sid\":\"f0b4299a4a0e\",\"short_id\":\"0\",\"token\":\"zJCU5kiu5UdPbxpN\",\"data\":\"{\\\"ip\\\":\\\"192.168.1.105\\\"}\"}"
        val result = sut.parseJson(json, AqaraNetMessage::class)
        result.fold(
            { _ ->
                fail()
            },
            {})
    }

    @Test
    fun testFailJsonParsing() {
        val json =
            "{\"cmd\":\"heartbeat\",\"model\":\"gateway\",\"sid\":\"f0b4299a4a0e\",\"short_id\":\"0\",\"token\":\"zJCU5kiu5UdPbxpN\",\"data\":{\"ip\":\"192.168.1.105\"}}"
        val result = sut.parseJson(json, AqaraNetMessage::class)
        result.fold(
            { _ -> },
            {
                fail()
            })
    }
}