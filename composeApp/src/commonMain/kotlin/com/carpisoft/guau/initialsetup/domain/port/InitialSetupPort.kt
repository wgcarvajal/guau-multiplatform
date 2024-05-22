package com.carpisoft.guau.initialsetup.domain.port

import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.initialsetup.domain.model.EmployeeResp
import com.carpisoft.guau.initialsetup.domain.model.EmployeesReq


interface InitialSetupPort {

    suspend fun getEmployees(token: String, employeesReq: EmployeesReq): Resp<List<EmployeeResp>>
}