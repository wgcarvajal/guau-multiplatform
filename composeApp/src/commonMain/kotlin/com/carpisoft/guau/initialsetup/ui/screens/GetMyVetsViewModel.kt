package com.carpisoft.guau.initialsetup.ui.screens

import androidx.compose.runtime.Composable
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.koin.compose.koinInject

@Composable
fun GetMyVetsViewModel(myVetsViewModel: MyVetsViewModel = koinInject()):MyVetsViewModel {
    return getViewModel(key = MyVetsViewModel.TAG, factory = viewModelFactory {
        myVetsViewModel
    })
}