package initialsetup.domain.port

import core.domain.model.Resp
import initialsetup.domain.model.EmployeeResp
import initialsetup.domain.model.EmployeesReq


interface InitialSetupPort {

    suspend fun getEmployees(token: String, employeesReq: EmployeesReq): Resp<List<EmployeeResp>>
}