package com.carpisoft.guau.pet.ui.screens

import androidx.compose.runtime.Composable
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.koin.compose.koinInject

@Composable
fun GetPetsViewModel(petsViewModel: PetsViewModel = koinInject()): PetsViewModel {
    return getViewModel(key = PetsViewModel.TAG, factory = viewModelFactory {
        petsViewModel
    })
}