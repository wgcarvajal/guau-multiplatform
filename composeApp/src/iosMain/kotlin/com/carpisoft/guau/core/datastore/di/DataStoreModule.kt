package com.carpisoft.guau.core.datastore.di

import com.carpisoft.guau.core.datastore.data.createDataStore
import org.koin.core.module.Module
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module

actual val dataStoreModule: Module = module {
    single {
        createDataStore()
    } withOptions {
        createdAtStart()
    }
}