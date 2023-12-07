package employee.data.repository

import core.data.preferences.IPreferences
import employee.data.EmployeePreferencesConstants
import employee.domain.port.EmployeePreferencesPort

class EmployeePreferencesRepository(private val preferences: IPreferences) :
    EmployeePreferencesPort {

    override suspend fun saveEmployeeId(employeeId: Long) {
        preferences.saveLong(EmployeePreferencesConstants.EMPLOYEE_ID_KEY, employeeId)
    }

    override suspend fun saveCenterId(centerId: Long) {
        preferences.saveLong(EmployeePreferencesConstants.CENTER_ID_KEY, centerId)
    }

    override suspend fun saveRol(rol: String) {
        preferences.saveString(EmployeePreferencesConstants.ROL_KEY, rol)
    }

    override suspend fun getEmployeeId(): Long {
        return preferences.getLong(EmployeePreferencesConstants.EMPLOYEE_ID_KEY)
    }

    override suspend fun getCenterId(): Long {
        return preferences.getLong(EmployeePreferencesConstants.CENTER_ID_KEY)
    }

    override suspend fun getRol(): String {
        return preferences.getString(EmployeePreferencesConstants.ROL_KEY)
    }
}