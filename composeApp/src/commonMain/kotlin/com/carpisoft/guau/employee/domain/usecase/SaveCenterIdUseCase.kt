package com.carpisoft.guau.employee.domain.usecase

import com.carpisoft.guau.employee.domain.port.EmployeePreferencesPort

class SaveCenterIdUseCase(private val employeePreferencesPort: EmployeePreferencesPort) {

    suspend operator fun invoke(centerId: String) {
        employeePreferencesPort.saveCenterId(centerId = centerId)
    }
}