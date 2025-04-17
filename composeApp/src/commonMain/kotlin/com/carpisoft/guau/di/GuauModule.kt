package com.carpisoft.guau.di

import com.carpisoft.guau.ui.AppViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val guauModule: Module = module {

    viewModelOf(
        ::AppViewModel
    )
}