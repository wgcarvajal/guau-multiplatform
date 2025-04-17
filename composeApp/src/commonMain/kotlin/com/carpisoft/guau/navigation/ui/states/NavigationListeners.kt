package com.carpisoft.guau.navigation.ui.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.carpisoft.guau.navigation

@Composable
fun BackListener(backAction: (NavigationAction) -> Unit) {
    LaunchedEffect(1) {
        navigation.events.collect { action ->
            if (action is NavigationAction.BackHandler) {
                backAction(action)
            }
        }
    }
}