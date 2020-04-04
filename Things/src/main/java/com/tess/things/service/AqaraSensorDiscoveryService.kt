package com.tess.things.service

import com.tess.things.interfaces.DataSubscriber
import com.tess.things.models.AqaraSensor

interface AqaraSensorDiscoveryService : DataSubscriber<List<AqaraSensor>> {

    fun discoverySensor()
}
