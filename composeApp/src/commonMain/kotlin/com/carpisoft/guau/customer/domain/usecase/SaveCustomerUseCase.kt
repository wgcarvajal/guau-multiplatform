package com.carpisoft.guau.customer.domain.usecase

import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.customer.domain.model.RegisterCustomerReq
import com.carpisoft.guau.customer.domain.model.RegisterCustomerResp
import com.carpisoft.guau.customer.domain.port.CustomerPort

class SaveCustomerUseCase(private val customerPort: CustomerPort) {

    suspend operator fun invoke(
        token: String,
        customerReq: RegisterCustomerReq
    ): Resp<RegisterCustomerResp> {
        return customerPort.save(token = token, customerReq = customerReq)
    }
}