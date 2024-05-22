package com.carpisoft.guau.core.di

import com.carpisoft.guau.core.domain.usecase.InitialsInCapitalLetterUseCase
import com.carpisoft.guau.core.domain.usecase.IsMaxStringSizeUseCase
import com.carpisoft.guau.core.domain.usecase.IsOnlyLettersUseCase
import com.carpisoft.guau.core.domain.usecase.IsOnlyNumbersUseCase
import com.carpisoft.guau.core.domain.usecase.RemoveInitialWhiteSpaceUseCase
import com.carpisoft.guau.core.domain.usecase.ValidateEmailAndPasswordUseCase
import com.carpisoft.guau.core.domain.usecase.ValidateEmailUseCase
import com.carpisoft.guau.core.domain.usecase.ValidateNameUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

val coreModule: Module = module {
    factory {
        InitialsInCapitalLetterUseCase()
    }
    factory {
        IsMaxStringSizeUseCase()
    }
    factory {
        IsOnlyLettersUseCase()
    }
    factory {
        IsOnlyNumbersUseCase()
    }
    factory {
        RemoveInitialWhiteSpaceUseCase()
    }
    factory {
        ValidateEmailAndPasswordUseCase()
    }
    factory {
        ValidateEmailUseCase()
    }
    factory {
        ValidateNameUseCase()
    }
}