package com.carpisoft.guau.core.preferences.di

import com.carpisoft.guau.core.preferences.data.PreferencesManagerImpl
import com.carpisoft.guau.core.preferences.domain.port.PreferencesManager
import org.koin.core.module.Module
import org.koin.dsl.module

val preferencesModule: Module = module {
    single<PreferencesManager> {
        PreferencesManagerImpl(dataStore = get())
    }
}