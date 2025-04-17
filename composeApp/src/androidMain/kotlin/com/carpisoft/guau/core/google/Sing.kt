package com.carpisoft.guau.core.google

import com.carpisoft.guau.login.ui.states.SocialLoginAction
import com.carpisoft.guau.socialLogin

fun onLoginWithGoogle(token: String) {
    socialLogin.send(SocialLoginAction.OnLoginWithGoogle(param = token))
}

fun onSignOutWithGoogle() {
    socialLogin.send(SocialLoginAction.OnSignOutWithGoogle())
}