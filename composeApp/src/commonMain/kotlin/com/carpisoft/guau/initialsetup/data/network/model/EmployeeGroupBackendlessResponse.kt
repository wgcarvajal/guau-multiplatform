package com.carpisoft.guau.initialsetup.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class EmployeeGroupBackendlessResponse(
    val items: List<EmployeeBackendlessResponse>
)
