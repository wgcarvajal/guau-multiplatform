package customer.domain.usecase

import core.domain.model.Resp
import customer.domain.model.CustomerResp
import customer.domain.port.CustomerPort

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