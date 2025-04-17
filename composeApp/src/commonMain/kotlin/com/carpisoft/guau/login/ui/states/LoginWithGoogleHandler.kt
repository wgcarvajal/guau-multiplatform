package com.carpisoft.guau.login.ui.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.carpisoft.guau.socialLogin

@Composable
fun LoginWithGoogleHandler(onLoginWithGoogle: (SocialLoginAction) -> Unit) {
    LaunchedEffect(1) {
        socialLogin.events.collect { action ->
            if (action is SocialLoginAction.OnLoginWithGoogle) {
                onLoginWithGoogle(action)
            }
        }
    }
}

@Composable
fun SignOutWithGoogleHandler(onSignOutWithGoogle: () -> Unit) {
    LaunchedEffect(1) {
        socialLogin.events.collect { action ->
            if (action is SocialLoginAction.OnSignOutWithGoogle) {
                onSignOutWithGoogle()
            }
        }
    }
}

@Composable
fun GoLoginWithGoogleHandler(goLoginWithGoogle: (SocialLoginAction) -> Unit) {
    LaunchedEffect(1) {
        socialLogin.events.collect { action ->
            if (action is SocialLoginAction.GoLoginWithGoogle) {
                goLoginWithGoogle(action)
            }
        }
    }
}

@Composable
fun GoSignOutWithGoogleHandler(goSignOutWithGoogle: (SocialLoginAction) -> Unit) {
    LaunchedEffect(1) {
        socialLogin.events.collect { action ->
            if (action is SocialLoginAction.GoSignOutWithGoogle) {
                goSignOutWithGoogle(action)
            }
        }
    }
}