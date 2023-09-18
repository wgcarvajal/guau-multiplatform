package employee.domain.usecase

import employee.domain.port.EmployeePreferencesPort

class SaveEmployeeIdUseCase(private val employeePreferencesPort: EmployeePreferencesPort) {

    suspend operator fun invoke(employeeId:Long)
    {
        employeePreferencesPort.saveEmployeeId(employeeId = employeeId)
    }
}