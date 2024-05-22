package com.carpisoft.guau.core.utils.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.carpisoft.guau.store

@Composable
fun LoginWithGoogleHandler(onLoginWithGoogle: (Action) -> Unit) {
    LaunchedEffect(1) {
        store.events.collect { action ->
            if (action is Action.OnLoginWithGoogle) {
                onLoginWithGoogle(action)
            }
        }
    }
}

@Composable
fun SignOutWithGoogleHandler(onSignOutWithGoogle: () -> Unit) {
    LaunchedEffect(1) {
        store.events.collect { action ->
            if (action is Action.OnSignOutWithGoogle) {
                onSignOutWithGoogle()
            }
        }
    }
}