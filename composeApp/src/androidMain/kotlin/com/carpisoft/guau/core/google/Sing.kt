package com.carpisoft.guau.core.google

import com.carpisoft.guau.core.utils.states.Action
import com.carpisoft.guau.store

fun onLoginWithGoogle(token: String) {
    store.send(Action.OnLoginWithGoogle(param = token))
}

fun onSignOutWithGoogle() {
    store.send(Action.OnSignOutWithGoogle())
}