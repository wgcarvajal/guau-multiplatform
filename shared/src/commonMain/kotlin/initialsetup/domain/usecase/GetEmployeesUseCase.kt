package initialsetup.domain.usecase

import core.domain.model.Resp
import initialsetup.domain.model.EmployeeResp
import initialsetup.domain.model.EmployeesReq
import initialsetup.domain.port.InitialSetupPort


class GetEmployeesUseCase(
    private val initialSetupPort: InitialSetupPort
) {

    suspend operator fun invoke(token: String, employeesReq: EmployeesReq): Resp<List<EmployeeResp>> {
        return initialSetupPort.getEmployees(token = token, employeesReq = employeesReq)
    }
}