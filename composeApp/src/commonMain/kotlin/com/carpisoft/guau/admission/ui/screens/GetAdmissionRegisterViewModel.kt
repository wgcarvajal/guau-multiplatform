package com.carpisoft.guau.admission.ui.screens

import androidx.compose.runtime.Composable
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.koin.compose.koinInject

@Composable
fun GetAdmissionRegisterViewModel(admissionRegisterViewModel: AdmissionRegisterViewModel = koinInject()): AdmissionRegisterViewModel {
    return getViewModel(
        AdmissionRegisterViewModel.TAG,
        viewModelFactory {
            admissionRegisterViewModel
        })
}