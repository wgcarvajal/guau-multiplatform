package com.carpisoft.guau.initialsetup.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class EmployeesBackendlessRequest(
    val groupBy: String,
    val where: String,
    val loadRelations: String
)
