package com.carpisoft.guau

import androidx.compose.ui.window.ComposeUIViewController
import com.carpisoft.guau.core.di.getListModule
import com.carpisoft.guau.core.utils.states.Action
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
    store.send(Action.OnLoginWithGoogle(param = token))
}

fun onSignOutWithGoogle() {
    store.send(Action.OnSignOutWithGoogle())
}
