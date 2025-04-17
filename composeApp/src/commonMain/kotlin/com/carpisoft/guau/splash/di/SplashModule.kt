package com.carpisoft.guau.splash.di

import com.carpisoft.guau.splash.domain.usecase.IsLoginTokenUseCase
import com.carpisoft.guau.splash.domain.usecase.IsSelectedVetUseCase
import com.carpisoft.guau.splash.ui.screens.SplashViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val splashModule: Module = module {
    factory {
        IsLoginTokenUseCase(loginAuthorizationPort = get())
    }

    factory {
        IsSelectedVetUseCase(employeePreferencesPort = get())
    }

    viewModelOf(
        ::SplashViewModel
    )
}