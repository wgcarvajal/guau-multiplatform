package splash.domain.usecase

import employee.domain.port.EmployeePreferencesPort

class IsSelectedVetUseCase(private val employeePreferencesPort: EmployeePreferencesPort) {

    suspend operator fun invoke() = employeePreferencesPort.getCenterId()!=-1L
}