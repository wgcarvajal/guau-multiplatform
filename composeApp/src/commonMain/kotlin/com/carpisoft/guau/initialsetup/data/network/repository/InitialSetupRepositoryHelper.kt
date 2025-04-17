package com.carpisoft.guau.initialsetup.data.network.repository

import com.carpisoft.guau.core.network.data.model.Response
import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.initialsetup.data.network.model.EmployeeGroupQueryBackendlessResponse
import com.carpisoft.guau.initialsetup.data.network.model.EmployeeResponse
import com.carpisoft.guau.initialsetup.domain.model.CenterResp
import com.carpisoft.guau.initialsetup.domain.model.EmployeeResp


open class InitialSetupRepositoryHelper {

    fun processResponse(response: Response<List<EmployeeResponse>>): Resp<List<EmployeeResp>> {
        val employeesResponse = response.data
        var data: List<EmployeeResp>? = null
        if (employeesResponse != null) {

            val employeesResp = mutableListOf<EmployeeResp>()
            for (employee in employeesResponse) {
                employeesResp.add(
                    EmployeeResp(
                        idEmployee = employee.idEmployee,
                        centerResp = CenterResp(
                            id = employee.centerResponse.id,
                            name = employee.centerResponse.name,
                            address = employee.centerResponse.address,
                            phone = employee.centerResponse.phone,
                            image = employee.centerResponse.image
                        ),
                        roles = employee.roles
                    )
                )
            }

            data = employeesResp
        }
        return Resp(
            isValid = response.isValid,
            error = response.error,
            errorCode = response.errorCode,
            data = data
        )
    }

    fun processBackendlessResponse(response: Response<EmployeeGroupQueryBackendlessResponse>): Resp<List<EmployeeResp>> {
        val employeeGroupQueryBackendlessResponse = response.data
        var data: List<EmployeeResp>? = null
        if (employeeGroupQueryBackendlessResponse != null) {
            val employeesResp = mutableListOf<EmployeeResp>()
            for (employeeGroupBackendlessResponse in employeeGroupQueryBackendlessResponse.items) {
                for (employeeBackendlessResponse in employeeGroupBackendlessResponse.items) {
                    employeesResp.add(
                        EmployeeResp(
                            idEmployee = employeeBackendlessResponse.idEmployee,
                            centerResp = CenterResp(
                                id = employeeBackendlessResponse.centerResponse.id,
                                name = employeeBackendlessResponse.centerResponse.name,
                                address = employeeBackendlessResponse.centerResponse.address,
                                phone = employeeBackendlessResponse.centerResponse.phone,
                                image = employeeBackendlessResponse.centerResponse.image
                            ),
                            roles = listOf(employeeBackendlessResponse.rol)
                        )
                    )
                }
            }
            data = employeesResp
        }
        return Resp(
            isValid = response.isValid,
            error = response.error,
            errorCode = response.errorCode,
            data = data
        )
    }
}