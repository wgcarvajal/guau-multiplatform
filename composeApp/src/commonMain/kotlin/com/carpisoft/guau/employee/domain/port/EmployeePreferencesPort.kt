package com.carpisoft.guau.employee.domain.port

interface EmployeePreferencesPort {

    suspend fun saveEmployeeId(employeeId: Long)
    suspend fun saveCenterId(centerId: Long)
    suspend fun saveRol(rol: String)
    suspend fun getEmployeeId(): Long
    suspend fun getCenterId(): Long
    suspend fun getRol(): String
}