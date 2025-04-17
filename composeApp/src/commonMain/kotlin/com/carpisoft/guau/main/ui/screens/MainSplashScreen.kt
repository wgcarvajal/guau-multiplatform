package com.carpisoft.guau.main.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator

class MainSplashScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        LaunchedEffect(key1 = 1) {
            navigator?.push(item = MainScreen())
        }
    }
}