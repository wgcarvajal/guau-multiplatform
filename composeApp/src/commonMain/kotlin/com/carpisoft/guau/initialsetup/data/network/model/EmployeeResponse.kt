package com.carpisoft.guau.initialsetup.data.network.model

import com.carpisoft.guau.initialsetup.data.network.model.CenterResponse
import kotlinx.serialization.Serializable

@Serializable
data class EmployeeResponse(
    val idEmployee:Long,
    val centerResponse: CenterResponse,
    val roles:List<String>
)
