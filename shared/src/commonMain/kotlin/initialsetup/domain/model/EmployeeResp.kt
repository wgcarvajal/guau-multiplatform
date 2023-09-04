package initialsetup.domain.model

data class EmployeeResp(
    val idEmployee: Long,
    val centerResp: CenterResp,
    val roles: List<String>
)