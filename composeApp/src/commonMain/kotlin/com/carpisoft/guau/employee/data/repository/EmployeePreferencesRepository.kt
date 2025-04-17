package com.carpisoft.guau.employee.data.repository

import com.carpisoft.guau.core.preferences.domain.port.PreferencesManager
import com.carpisoft.guau.employee.data.EmployeePreferencesConstants
import com.carpisoft.guau.employee.domain.port.EmployeePreferencesPort

class EmployeePreferencesRepository(private val preferences: PreferencesManager) :
    EmployeePreferencesPort {

    override suspend fun saveEmployeeId(employeeId: String) {
        preferences.saveString(EmployeePreferencesConstants.EMPLOYEE_ID_KEY, employeeId)
    }

    override suspend fun saveCenterId(centerId: String) {
        preferences.saveString(EmployeePreferencesConstants.CENTER_ID_KEY, centerId)
    }

    override suspend fun saveRol(rol: String) {
        preferences.saveString(EmployeePreferencesConstants.ROL_KEY, rol)
    }

    override suspend fun getEmployeeId(): String {
        return preferences.getString(EmployeePreferencesConstants.EMPLOYEE_ID_KEY)
    }

    override suspend fun getCenterId(): String {
        return preferences.getString(EmployeePreferencesConstants.CENTER_ID_KEY)
    }

    override suspend fun getRol(): String {
        return preferences.getString(EmployeePreferencesConstants.ROL_KEY)
    }
}