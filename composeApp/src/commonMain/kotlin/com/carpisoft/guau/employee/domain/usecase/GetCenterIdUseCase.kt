package com.carpisoft.guau.employee.domain.usecase

import com.carpisoft.guau.employee.domain.port.EmployeePreferencesPort

class GetCenterIdUseCase (private val employeePreferencesPort: EmployeePreferencesPort) {
    suspend operator fun invoke():String {
        return employeePreferencesPort.getCenterId()
    }
}