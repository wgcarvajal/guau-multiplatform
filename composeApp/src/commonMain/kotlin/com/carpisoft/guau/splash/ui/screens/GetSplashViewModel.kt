package com.carpisoft.guau.splash.ui.screens

import androidx.compose.runtime.Composable
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.koin.compose.koinInject

@Composable
fun GetSplashViewModel(
    splashViewModel: SplashViewModel = koinInject()
): SplashViewModel {
    return getViewModel(
        SplashViewModel.TAG,
        viewModelFactory {
            splashViewModel
        })
}