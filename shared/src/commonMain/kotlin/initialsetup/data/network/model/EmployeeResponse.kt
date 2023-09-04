package initialsetup.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class EmployeeResponse(
    val idEmployee:Long,
    val centerResponse:CenterResponse,
    val roles:List<String>
)
