package com.carpisoft.guau.customer.data.network.repository

import com.carpisoft.guau.core.network.data.model.Response
import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.customer.data.network.model.CustomerResponse
import com.carpisoft.guau.customer.data.network.model.RegisterCustomerResponse
import com.carpisoft.guau.customer.domain.model.CustomerResp
import com.carpisoft.guau.customer.domain.model.IdentificationTypeResp
import com.carpisoft.guau.customer.domain.model.RegisterCustomerResp

open class CustomerRepositoryHelper {
    fun processResponse(response: Response<RegisterCustomerResponse>): Resp<RegisterCustomerResp> {
        val customerResponse = response.data
        var data: RegisterCustomerResp? = null
        if (customerResponse != null) {
            data = RegisterCustomerResp(
                id = customerResponse.id,
            )
        }
        return Resp(
            isValid = response.isValid,
            error = response.error,
            param = response.param,
            errorCode = response.errorCode,
            data = data
        )
    }

    fun processCustomerResponse(response: Response<List<CustomerResponse>>): Resp<List<CustomerResp>> {
        val customerResponse = response.data
        var data: List<CustomerResp>? = null
        if (customerResponse != null) {
            val customersResp = mutableListOf<CustomerResp>()
            for (customer in customerResponse) {
                customersResp.add(
                    CustomerResp(
                        id = customer.id,
                        identificationType = IdentificationTypeResp(
                            customer.identificationType.id,
                            customer.identificationType.name
                        ),
                        identification = customer.identification,
                        name = customer.name,
                        lastName = customer.lastName,
                        email = customer.email,
                        address = customer.address,
                        phone = customer.phone
                    )
                )
            }
            data = customersResp
        }
        return Resp(response.isValid, response.error, response.param, response.errorCode, data)
    }
}