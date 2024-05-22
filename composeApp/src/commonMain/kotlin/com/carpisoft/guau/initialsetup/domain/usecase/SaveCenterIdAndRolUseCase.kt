package com.carpisoft.guau.initialsetup.domain.usecase

import com.carpisoft.guau.employee.domain.port.EmployeePreferencesPort

class SaveCenterIdAndRolUseCase(private val employeePreferencesPort: EmployeePreferencesPort) {
    suspend operator fun invoke(employeeId: Long, centerId: Long, rol: String) {
        employeePreferencesPort.saveRol(rol)
        employeePreferencesPort.saveCenterId(centerId)
        employeePreferencesPort.saveEmployeeId(employeeId)
    }
}