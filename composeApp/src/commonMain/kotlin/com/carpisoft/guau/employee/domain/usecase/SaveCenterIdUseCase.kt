package com.carpisoft.guau.employee.domain.usecase

import com.carpisoft.guau.employee.domain.port.EmployeePreferencesPort

class SaveCenterIdUseCase(private val employeePreferencesPort: EmployeePreferencesPort) {

    suspend operator fun invoke(centerId: Long) {
        employeePreferencesPort.saveCenterId(centerId = centerId)
    }
}