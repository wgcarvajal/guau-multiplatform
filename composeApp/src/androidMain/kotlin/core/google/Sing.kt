package core.google

import core.utils.states.Action
import store

fun onLoginWithGoogle(token: String) {
    store.send(Action.OnLoginWithGoogle(param = token))
}

fun onSignOutWithGoogle() {
    store.send(Action.OnSignOutWithGoogle())
}