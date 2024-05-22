package com.carpisoft.guau.ui

import androidx.compose.runtime.Composable
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.koin.compose.koinInject

@Composable
fun GetAppViewModel(appViewModel: AppViewModel = koinInject()): AppViewModel {
    return getViewModel(key = AppViewModel.TAG, factory = viewModelFactory {
        appViewModel
    })
}