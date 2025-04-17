package com.carpisoft.guau.customer.di

import com.carpisoft.guau.customer.data.network.repository.CustomerRepository
import com.carpisoft.guau.customer.data.network.repository.IdentificationTypeRepository
import com.carpisoft.guau.customer.domain.port.CustomerPort
import com.carpisoft.guau.customer.domain.port.IdentificationTypePort
import com.carpisoft.guau.customer.domain.usecase.GetAllIdentificationTypeUseCase
import com.carpisoft.guau.customer.domain.usecase.GetCustomersByCenterIdAndNameWithPaginationAndSortUseCase
import com.carpisoft.guau.customer.domain.usecase.GetCustomersByCenterIdWithPaginationAndSortUseCase
import com.carpisoft.guau.customer.domain.usecase.SaveCustomerUseCase
import com.carpisoft.guau.customer.ui.AddCustomerViewModel
import com.carpisoft.guau.customer.ui.CustomerViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val customerModule: Module = module {

    factory<CustomerPort> {
        CustomerRepository(httpClient = get())
    }

    factory<IdentificationTypePort> {
        IdentificationTypeRepository(httpClient = get())
    }

    factory {
        GetAllIdentificationTypeUseCase(identificationTypePort = get())
    }

    factory {
        GetCustomersByCenterIdAndNameWithPaginationAndSortUseCase(customerPort = get())
    }

    factory {
        GetCustomersByCenterIdWithPaginationAndSortUseCase(customerPort = get())
    }

    factory {
        SaveCustomerUseCase(customerPort = get())
    }

    viewModelOf(::CustomerViewModel)
    viewModelOf(::AddCustomerViewModel)
}