package com.carpisoft.guau.customer.ui

import androidx.compose.runtime.Composable
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.koin.compose.koinInject

@Composable
fun GetAddCustomerViewModel(addCustomerViewModel: AddCustomerViewModel = koinInject()):AddCustomerViewModel {
    return getViewModel(key = AddCustomerViewModel.TAG, factory = viewModelFactory {
        addCustomerViewModel
    })
}