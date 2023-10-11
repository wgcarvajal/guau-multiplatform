package initialsetup.data.network.repository

import core.data.network.model.Response
import core.domain.model.Resp
import initialsetup.data.network.model.EmployeeResponse
import initialsetup.domain.model.CenterResp
import initialsetup.domain.model.EmployeeResp


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
}