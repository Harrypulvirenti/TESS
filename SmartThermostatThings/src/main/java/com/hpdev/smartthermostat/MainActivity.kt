package com.hpdev.smartthermostat

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.PeripheralManager
import com.hpdev.sdk.logging.SmartLogger
import com.hpdev.smartthermostat.viewmodel.TemperatureSensorViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : FragmentActivity() {

    private val temperatureViewModel: TemperatureSensorViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val manager = PeripheralManager.getInstance()
        val portList: List<String> = manager.gpioList
        if (portList.isEmpty()) {
            SmartLogger.i("No GPIO port available on this device.")
        } else {
            SmartLogger.i("List of available ports: $portList")
        }

        val gpio = manager.openGpio("BCM17")
        gpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
        gpio.value = true

//        val database = FirebaseDatabase.getInstance()
//        val myRef = database.getReference("state")
//        myRef.addValueEventListener(object : ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//                SmartLogger.d("HERE DATA CHANGED : " + p0.value)
//
//                gpio.value = p0.value as Boolean
//            }
//        })

        temperatureViewModel.requestTemperature()
    }
}

