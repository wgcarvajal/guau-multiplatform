package com.carpisoft.guau.initialsetup.domain.model

data class EmployeeResp(
    val idEmployee: String,
    val centerResp: CenterResp,
    val roles: List<String>
)