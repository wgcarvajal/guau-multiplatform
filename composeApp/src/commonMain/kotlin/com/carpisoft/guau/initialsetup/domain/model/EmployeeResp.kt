package com.carpisoft.guau.initialsetup.domain.model

import com.carpisoft.guau.initialsetup.domain.model.CenterResp

data class EmployeeResp(
    val idEmployee: Long,
    val centerResp: CenterResp,
    val roles: List<String>
)