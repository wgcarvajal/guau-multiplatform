package com.carpisoft.guau.splash.domain.usecase

import com.carpisoft.guau.employee.domain.port.EmployeePreferencesPort

class IsSelectedVetUseCase(private val employeePreferencesPort: EmployeePreferencesPort) {

    suspend operator fun invoke() = employeePreferencesPort.getCenterId()!=""
}