package com.carpisoft.guau.login.ui.states

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

sealed class SocialLoginAction(val param: String) {

    class GoSignOutWithGoogle() : SocialLoginAction(param = "")
    class GoLoginWithGoogle() : SocialLoginAction(param = "")
    class OnLoginWithGoogle(param: String) : SocialLoginAction(param = param)
    class OnSignOutWithGoogle() : SocialLoginAction(param = "")
}

interface SocialLogin {
    fun send(action: SocialLoginAction)
    val events: SharedFlow<SocialLoginAction>
}

fun CoroutineScope.createSocialLogin(): SocialLogin {
    val events = MutableSharedFlow<SocialLoginAction>()

    return object : SocialLogin {
        override fun send(action: SocialLoginAction) {
            launch {
                events.emit(action)
            }
        }

        override val events: SharedFlow<SocialLoginAction> = events.asSharedFlow()
    }
}