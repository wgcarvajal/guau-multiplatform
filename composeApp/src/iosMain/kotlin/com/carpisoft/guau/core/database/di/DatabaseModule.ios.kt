package com.carpisoft.guau.core.database.di

import com.carpisoft.guau.core.database.data.DriverFactory
import com.carpisoft.guau.core.database.data.getDatabase
import org.koin.core.module.Module
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module

actual val databaseModule: Module = module {

    factory {
        DriverFactory()
    }

    single {
        getDatabase(driverFactory = get())
    } withOptions {
        createdAtStart()
    }
}