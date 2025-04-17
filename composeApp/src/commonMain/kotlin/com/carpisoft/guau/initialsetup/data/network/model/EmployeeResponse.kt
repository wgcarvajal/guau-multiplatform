package com.carpisoft.guau.initialsetup.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class EmployeeResponse(
    val idEmployee: String,
    val centerResponse: CenterResponse,
    val roles: List<String>
)
