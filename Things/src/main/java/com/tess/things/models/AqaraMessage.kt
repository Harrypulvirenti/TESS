package com.tess.things.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tess.things.models.aqara.AqaraNetMessage

const val AQARA_NETWORK_MESSAGES_TABLE = "AQARA_NETWORK_MESSAGES"

@Entity(tableName = AQARA_NETWORK_MESSAGES_TABLE)
data class AqaraMessage(
    @PrimaryKey
    @ColumnInfo(name = "timestamp")
    val timestamp: Long,
    @ColumnInfo(name = "command_name")
    val commandName: String? = null,
    @ColumnInfo(name = "model")
    val model: String? = null,
    @ColumnInfo(name = "sid")
    val sid: String? = null,
    @ColumnInfo(name = "short_id")
    val shortId: String? = null,
    @ColumnInfo(name = "temperature")
    val temperature: String? = null,
    @ColumnInfo(name = "pressure")
    val pressure: String? = null,
    @ColumnInfo(name = "humidity")
    val humidity: String? = null,
    @ColumnInfo(name = "rgb")
    val rgb: String? = null,
    @ColumnInfo(name = "illumination")
    val illumination: String? = null,
    @ColumnInfo(name = "ip")
    val ip: String? = null,
    @ColumnInfo(name = "data_json")
    val dataJson: String? = null
) {
    constructor(timestamp: Long, aqaraMessage: AqaraNetMessage) : this(
        timestamp = timestamp,
        commandName = aqaraMessage.commandName,
        model = aqaraMessage.model,
        sid = aqaraMessage.sid,
        shortId = aqaraMessage.shortId,
        temperature = aqaraMessage.data?.temperature,
        pressure = aqaraMessage.data?.pressure,
        humidity = aqaraMessage.data?.humidity,
        rgb = aqaraMessage.data?.rgb,
        illumination = aqaraMessage.data?.illumination,
        ip = aqaraMessage.data?.ip,
        dataJson = aqaraMessage.dataJson
    )
}