package customer.domain.port

import core.domain.model.Resp
import customer.domain.model.CustomerResp
import customer.domain.model.RegisterCustomerReq
import customer.domain.model.RegisterCustomerResp

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