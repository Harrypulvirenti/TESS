package com.tess.core.modules

import com.tess.core.network.ObjectParser
import com.tess.core.network.ObjectParserImpl
import org.koin.dsl.module

val networkModule = module {

    factory<ObjectParser> { ObjectParserImpl() }
}
