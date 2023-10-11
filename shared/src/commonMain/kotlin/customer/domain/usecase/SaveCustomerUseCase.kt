package customer.domain.usecase

import core.domain.model.Resp
import customer.domain.model.RegisterCustomerReq
import customer.domain.model.RegisterCustomerResp
import customer.domain.port.CustomerPort

class SaveCustomerUseCase(private val customerPort: CustomerPort) {

    suspend operator fun invoke(token: String, customerReq: RegisterCustomerReq): Resp<RegisterCustomerResp> {
        return customerPort.save(token = token, customerReq = customerReq)
    }
}