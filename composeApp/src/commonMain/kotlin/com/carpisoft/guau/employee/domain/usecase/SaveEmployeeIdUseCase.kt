package com.carpisoft.guau.employee.domain.usecase

import com.carpisoft.guau.employee.domain.port.EmployeePreferencesPort

class SaveEmployeeIdUseCase(private val employeePreferencesPort: EmployeePreferencesPort) {

    suspend operator fun invoke(employeeId:Long)
    {
        employeePreferencesPort.saveEmployeeId(employeeId = employeeId)
    }
}