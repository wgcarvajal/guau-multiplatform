package com.carpisoft.guau.customer.domain.usecase

import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.customer.domain.model.CustomerResp
import com.carpisoft.guau.customer.domain.port.CustomerPort

class GetCustomersByCenterIdAndNameWithPaginationAndSortUseCase(private val customerPort: CustomerPort) {

    suspend operator fun invoke(
        token: String,
        centerId: Int,
        search: String,
        page: Int,
        limit: Int
    ): Resp<List<CustomerResp>> {
        return customerPort.getCustomersByCenterIdAndNameWithPaginationAndSort(
            token = token,
            centerId = centerId,
            search = search,
            page = page,
            limit = limit
        )
    }
}