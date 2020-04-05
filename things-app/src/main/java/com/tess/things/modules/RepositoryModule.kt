package com.tess.things.modules

import com.tess.things.database.repository.AqaraMessageRepository
import com.tess.things.database.repository.AqaraMessageRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    single<AqaraMessageRepository> { AqaraMessageRepositoryImpl(get()) }
}
