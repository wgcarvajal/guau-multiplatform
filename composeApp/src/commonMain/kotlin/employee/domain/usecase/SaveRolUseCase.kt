package employee.domain.usecase

import employee.domain.port.EmployeePreferencesPort

class SaveRolUseCase(private val employeePreferencesPort: EmployeePreferencesPort) {

    suspend operator fun invoke(rol: String) {
        employeePreferencesPort.saveRol(rol = rol)
    }
}