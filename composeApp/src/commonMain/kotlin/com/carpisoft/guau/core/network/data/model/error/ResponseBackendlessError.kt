package com.carpisoft.guau.core.network.data.model.error

import kotlinx.serialization.Serializable

@Serializable
data class ResponseBackendlessError(
    val message: String,
    val code: Int
)