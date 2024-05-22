package com.carpisoft.guau.customer.domain.port

import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.customer.domain.model.CustomerResp
import com.carpisoft.guau.customer.domain.model.RegisterCustomerReq
import com.carpisoft.guau.customer.domain.model.RegisterCustomerResp

interface CustomerPort {
    suspend fun save(token: String, customerReq: RegisterCustomerReq): Resp<RegisterCustomerResp>

    suspend fun getCustomersByCenterIdAndNameWithPaginationAndSort(
        token: String,
        centerId: Int,
        search: String,
        page: Int,
        limit: Int
    ): Resp<List<CustomerResp>>

    suspend fun getCustomersByCenterIdWithPaginationAndSort(
        token: String,
        centerId: Int,
        page: Int,
        limit: Int
    ): Resp<List<CustomerResp>>
}