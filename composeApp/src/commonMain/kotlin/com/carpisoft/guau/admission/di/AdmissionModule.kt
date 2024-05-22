package com.carpisoft.guau.admission.di

import com.carpisoft.guau.admission.ui.screens.AdmissionRegisterViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val admissionModule: Module = module {
    factory {
        AdmissionRegisterViewModel()
    }
}