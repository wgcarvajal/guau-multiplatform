package com.carpisoft.guau.initialsetup.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmployeeBackendlessResponse (
    @SerialName("objectId") val idEmployee: String,
    @SerialName("center")val centerResponse: CenterResponse,
    val rol: String
)