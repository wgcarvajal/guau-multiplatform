package com.carpisoft.guau.admission.di

import com.carpisoft.guau.admission.ui.screens.AdmissionRegisterViewModel
import com.carpisoft.guau.admission.ui.util.AdmissionHelper
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val admissionModule: Module = module {
    single {
        AdmissionHelper()
    }
    viewModelOf(::AdmissionRegisterViewModel)
}