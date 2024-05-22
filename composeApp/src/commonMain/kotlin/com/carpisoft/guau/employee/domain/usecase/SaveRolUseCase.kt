package com.carpisoft.guau.employee.domain.usecase

import com.carpisoft.guau.employee.domain.port.EmployeePreferencesPort

class SaveRolUseCase(private val employeePreferencesPort: EmployeePreferencesPort) {

    suspend operator fun invoke(rol: String) {
        employeePreferencesPort.saveRol(rol = rol)
    }
}