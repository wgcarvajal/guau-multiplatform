package com.carpisoft.guau.employee.di

import com.carpisoft.guau.employee.data.repository.EmployeePreferencesRepository
import com.carpisoft.guau.employee.domain.port.EmployeePreferencesPort
import com.carpisoft.guau.employee.domain.usecase.GetCenterIdUseCase
import com.carpisoft.guau.employee.domain.usecase.SaveCenterIdUseCase
import com.carpisoft.guau.employee.domain.usecase.SaveEmployeeIdUseCase
import com.carpisoft.guau.employee.domain.usecase.SaveRolUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

val employeeModule: Module = module {

    factory<EmployeePreferencesPort> {
        EmployeePreferencesRepository(preferences = get())
    }

    factory {
        GetCenterIdUseCase(employeePreferencesPort = get())
    }

    factory {
        SaveCenterIdUseCase(employeePreferencesPort = get())
    }
    factory {
        SaveEmployeeIdUseCase(employeePreferencesPort = get())
    }

    factory {
        SaveRolUseCase(employeePreferencesPort = get())
    }
}