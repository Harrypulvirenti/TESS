package com.hpdev.smartthermostatcore.modules

import com.hpdev.smartthermostatcore.network.ObjectParser
import com.hpdev.smartthermostatcore.network.ObjectParserImpl
import org.koin.dsl.module

val networkModule = module {

    single { ObjectParserImpl() as ObjectParser }

}