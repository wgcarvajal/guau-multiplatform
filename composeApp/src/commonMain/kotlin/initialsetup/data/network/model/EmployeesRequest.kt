package initialsetup.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class EmployeesRequest(
    val email: String,
    val rol: String
)