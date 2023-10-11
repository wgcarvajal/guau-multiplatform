package customer.domain.usecase

import core.domain.model.Resp
import customer.domain.model.CustomerResp
import customer.domain.port.CustomerPort

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