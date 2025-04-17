package com.carpisoft.guau

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.carpisoft.guau.login.ui.states.GoLoginWithGoogleHandler
import com.carpisoft.guau.login.ui.states.GoSignOutWithGoogleHandler
import com.carpisoft.guau.login.ui.states.createSocialLogin
import com.carpisoft.guau.main.ui.states.createMain
import com.carpisoft.guau.navigation.ui.backPressed
import com.carpisoft.guau.navigation.ui.states.createNavigation
import com.carpisoft.guau.splash.ui.screens.SplashScreen
import com.carpisoft.guau.ui.theme.GuauTheme
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.age
import guau.composeapp.generated.resources.compose_multiplatform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.KoinContext

val socialLogin = CoroutineScope(SupervisorJob()).createSocialLogin()
val main = CoroutineScope(SupervisorJob()).createMain()
val navigation = CoroutineScope(SupervisorJob()).createNavigation()

@Composable
fun App2() {
    MaterialTheme {
        var greetingText by remember { mutableStateOf("Hello World!") }
        var showImage by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                greetingText = "Compose: ${Greeting().greet()}"
                showImage = !showImage
            }) {
                Text(stringResource(Res.string.age))
            }
            AnimatedVisibility(showImage) {
                Image(
                    painterResource(Res.drawable.compose_multiplatform),
                    null
                )
            }
        }
    }
}

@Composable
fun App(
    finishCallback: (() -> Unit)? = null,
    loginWithGoogle: () -> Unit,
    signOutWithGoogle: () -> Unit
) {
    KoinContext {
        AppContent(
            finishCallback = finishCallback,
            loginWithGoogle = loginWithGoogle,
            signOutWithGoogle = signOutWithGoogle
        )
    }
}

@Composable
private fun AppContent(
    finishCallback: (() -> Unit)?,
    loginWithGoogle: () -> Unit,
    signOutWithGoogle: () -> Unit
) {
    GuauTheme {
        Navigator(
            screen = SplashScreen(),
            onBackPressed = { currentScreen ->
                backPressed(currentScreen = currentScreen)
            }
        )
        { navigator: Navigator ->
            SlideTransition(navigator)
        }
    }

    GoLoginWithGoogleHandler {
        loginWithGoogle()
    }

    GoSignOutWithGoogleHandler {
        signOutWithGoogle()
    }

}