package com.carpisoft.guau.login.ui.screens

import androidx.compose.runtime.Composable
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.koin.compose.koinInject

@Composable
fun GetSignUpViewModel(signUpViewModel: SignUpViewModel = koinInject()): SignUpViewModel {
    return getViewModel(key = SignUpViewModel.TAG, factory = viewModelFactory {
        signUpViewModel
    })
}