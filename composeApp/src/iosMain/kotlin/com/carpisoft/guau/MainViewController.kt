package com.carpisoft.guau

import androidx.compose.ui.window.ComposeUIViewController
import com.carpisoft.guau.core.di.getListModule
import com.carpisoft.guau.login.ui.states.SocialLoginAction
import org.koin.core.context.startKoin

fun MainViewController(
    loginWithGoogle: () -> Unit,
    signOutWithGoogle: () -> Unit
) = ComposeUIViewController {
    App(
        loginWithGoogle = loginWithGoogle,
        signOutWithGoogle = signOutWithGoogle
    )
}

fun initKoin() {
    startKoin {
        modules(
            modules = getListModule()
        )
    }
}

fun onLoginWithGoogle(token: String) {
    socialLogin.send(SocialLoginAction.OnLoginWithGoogle(param = token))
}

fun onSignOutWithGoogle() {
    socialLogin.send(SocialLoginAction.OnSignOutWithGoogle())
}
