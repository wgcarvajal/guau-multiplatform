package com.carpisoft.guau.main.ui.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.carpisoft.guau.main

@Composable
fun NavigateScreenListener(navigateScreen: (MainAction) -> Unit) {
    LaunchedEffect(1) {
        main.events.collect { action ->
            if (action is MainAction.NavigateScreen) {
                navigateScreen(action)
            }
        }
    }
}