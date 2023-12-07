package employee.domain.usecase

import employee.domain.port.EmployeePreferencesPort

class GetCenterIdUseCase (private val employeePreferencesPort: EmployeePreferencesPort) {
    suspend operator fun invoke():Long {
        return employeePreferencesPort.getCenterId()
    }
}