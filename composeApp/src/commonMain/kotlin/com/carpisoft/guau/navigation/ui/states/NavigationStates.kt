package com.carpisoft.guau.navigation.ui.states

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

sealed class NavigationAction(param: String) {
    class BackHandler() : NavigationAction(param = "")
}

interface Navigation {
    fun send(action: NavigationAction)
    val events: SharedFlow<NavigationAction>
}

fun CoroutineScope.createNavigation(): Navigation {
    val events = MutableSharedFlow<NavigationAction>()

    return object : Navigation {
        override fun send(action: NavigationAction) {
            launch {
                events.emit(action)
            }
        }

        override val events: SharedFlow<NavigationAction> = events.asSharedFlow()
    }
}