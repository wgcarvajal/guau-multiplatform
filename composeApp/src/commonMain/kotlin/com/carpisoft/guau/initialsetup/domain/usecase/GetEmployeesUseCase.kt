package com.carpisoft.guau.initialsetup.domain.usecase

import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.initialsetup.domain.model.EmployeeResp
import com.carpisoft.guau.initialsetup.domain.model.EmployeesReq
import com.carpisoft.guau.initialsetup.domain.port.InitialSetupPort


class GetEmployeesUseCase(
    private val initialSetupPort: InitialSetupPort
) {

    suspend operator fun invoke(
        token: String,
        employeesReq: EmployeesReq
    ): Resp<List<EmployeeResp>> {
        return initialSetupPort.getEmployees(token = token, employeesReq = employeesReq)
    }
}