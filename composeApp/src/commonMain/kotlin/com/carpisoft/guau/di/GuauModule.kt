package com.carpisoft.guau.di

import com.carpisoft.guau.ui.AppViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val guauModule: Module = module {
    factory {
        AppViewModel(
            saveEmailUseCase = get(),
            saveNameUseCase = get(),
            saveTokenUseCase = get(),
            saveEmployeeIdUseCase = get(),
            saveCenterIdUseCase = get(),
            saveRolUseCase = get()
        )
    }
}