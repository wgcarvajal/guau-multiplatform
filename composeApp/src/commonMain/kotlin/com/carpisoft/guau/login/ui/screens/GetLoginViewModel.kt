package com.carpisoft.guau.login.ui.screens

import androidx.compose.runtime.Composable
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.koin.compose.koinInject

@Composable
fun GetLoginViewModel(loginViewModel: LoginViewModel = koinInject()): LoginViewModel {
    return getViewModel(key = LoginViewModel.TAG, factory = viewModelFactory { loginViewModel })
}