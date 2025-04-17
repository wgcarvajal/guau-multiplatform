package com.carpisoft.guau.initialsetup.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class EmployeesRequest(
    val id: String,
    val rol: String
)