package com.carpisoft.guau.login.di

import com.carpisoft.guau.login.data.repository.LoginAuthorizationRepository
import com.carpisoft.guau.login.data.repository.LoginRepository
import com.carpisoft.guau.login.data.repository.SignUpRepository
import com.carpisoft.guau.login.data.repository.SocialLoginRepository
import com.carpisoft.guau.login.domain.port.LoginAuthorizationPort
import com.carpisoft.guau.login.domain.port.LoginPort
import com.carpisoft.guau.login.domain.port.SignUpPort
import com.carpisoft.guau.login.domain.port.SocialLoginPort
import com.carpisoft.guau.login.domain.usecase.DoLoginUseCase
import com.carpisoft.guau.login.domain.usecase.DoRegisterUseCase
import com.carpisoft.guau.login.domain.usecase.DoSocialLoginUseCase
import com.carpisoft.guau.login.domain.usecase.GetEmailUseCase
import com.carpisoft.guau.login.domain.usecase.GetTokenUseCase
import com.carpisoft.guau.login.domain.usecase.SaveEmailUseCase
import com.carpisoft.guau.login.domain.usecase.SaveNameUseCase
import com.carpisoft.guau.login.domain.usecase.SaveTokenUseCase
import com.carpisoft.guau.login.ui.screens.LoginViewModel
import com.carpisoft.guau.login.ui.screens.SignUpViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val loginModule: Module = module {
    factory<LoginAuthorizationPort> {
        LoginAuthorizationRepository(preferences = get())
    }
    factory<LoginPort> {
        LoginRepository(httpClient = get())
    }

    factory<SignUpPort> {
        SignUpRepository(httpClient = get())
    }
    factory<SocialLoginPort> {
        SocialLoginRepository(httpClient = get())
    }
    factory { DoLoginUseCase(loginPort = get()) }

    factory { DoRegisterUseCase(signUpPort = get()) }

    factory { DoSocialLoginUseCase(socialLoginPort = get()) }

    factory { GetEmailUseCase(loginAuthorizationPort = get()) }

    factory { GetTokenUseCase(loginAuthorizationPort = get()) }

    factory { SaveEmailUseCase(loginAuthorizationPort = get()) }

    factory { SaveNameUseCase(loginAuthorizationPort = get()) }

    factory { SaveTokenUseCase(loginAuthorizationPort = get()) }

    factory {
        LoginViewModel(
            validateEmailAndPasswordUseCase = get(),
            doLoginUseCase = get(),
            doSocialLoginUseCase = get(),
            saveEmailUseCase = get(),
            saveNameUseCase = get(),
            saveTokenUseCase = get()
        )
    }

    factory {
        SignUpViewModel(
            validateEmailAndPasswordUseCase = get(),
            doRegisterUseCase = get(),
            initialsInCapitalLetterUseCase = get(),
            removeInitialWhiteSpaceUseCase = get(),
            isOnlyLettersUseCase = get(),
            isMaxStringSizeUseCase = get(),
            validateNameUseCase = get()
        )
    }
}