package com.carpisoft.guau.initialsetup.di

import com.carpisoft.guau.initialsetup.data.network.repository.InitialSetupBackendlessRepository
import com.carpisoft.guau.initialsetup.domain.port.InitialSetupPort
import com.carpisoft.guau.initialsetup.domain.usecase.GetEmployeesUseCase
import com.carpisoft.guau.initialsetup.domain.usecase.SaveCenterIdAndRolUseCase
import com.carpisoft.guau.initialsetup.ui.screens.MyVetsViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val initialSetupModule: Module = module {

    factory<InitialSetupPort> {
        InitialSetupBackendlessRepository(httpClient = get())
    }

    factory {
        GetEmployeesUseCase(initialSetupPort = get())
    }

    factory {
        SaveCenterIdAndRolUseCase(employeePreferencesPort = get())
    }
    viewModelOf(::MyVetsViewModel)
}