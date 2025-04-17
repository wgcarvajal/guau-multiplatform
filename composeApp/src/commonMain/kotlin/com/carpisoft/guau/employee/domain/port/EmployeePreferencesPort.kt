package com.carpisoft.guau.employee.domain.port

interface EmployeePreferencesPort {

    suspend fun saveEmployeeId(employeeId: String)
    suspend fun saveCenterId(centerId: String)
    suspend fun saveRol(rol: String)
    suspend fun getEmployeeId(): String
    suspend fun getCenterId(): String
    suspend fun getRol(): String
}