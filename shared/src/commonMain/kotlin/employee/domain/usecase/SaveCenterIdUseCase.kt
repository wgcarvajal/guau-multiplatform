package employee.domain.usecase

import employee.domain.port.EmployeePreferencesPort

class SaveCenterIdUseCase(private val employeePreferencesPort: EmployeePreferencesPort) {

    suspend operator fun invoke(centerId: Long) {
        employeePreferencesPort.saveCenterId(centerId = centerId)
    }
}