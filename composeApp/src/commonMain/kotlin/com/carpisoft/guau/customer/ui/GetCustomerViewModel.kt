package com.carpisoft.guau.customer.ui

import androidx.compose.runtime.Composable
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.koin.compose.koinInject

@Composable
fun GetCustomerViewModel(customerViewModel: CustomerViewModel = koinInject()):CustomerViewModel {
    return getViewModel(key = CustomerViewModel.TAG, factory = viewModelFactory {
        customerViewModel
    })
}