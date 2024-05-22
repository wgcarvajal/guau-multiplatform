package com.carpisoft.guau.customer.domain.usecase

import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.customer.domain.model.CustomerResp
import com.carpisoft.guau.customer.domain.port.CustomerPort

class GetCustomersByCenterIdWithPaginationAndSortUseCase(private val customerPort: CustomerPort) {

    suspend operator fun invoke(
        token: String,
        centerId: Int,
        page: Int,
        limit: Int
    ): Resp<List<CustomerResp>> {
        return customerPort.getCustomersByCenterIdWithPaginationAndSort(
            token = token,
            centerId = centerId,
            page = page,
            limit = limit
        )
    }
}