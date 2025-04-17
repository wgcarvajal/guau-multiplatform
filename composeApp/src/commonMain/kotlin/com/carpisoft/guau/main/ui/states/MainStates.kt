package com.carpisoft.guau.main.ui.states

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

sealed class MainAction(val param: String = "", val json: String = "") {
    class NavigateScreen(tag: String, json: String = "") : MainAction(param = tag, json = json)
}

interface Main {
    fun send(action: MainAction)
    val events: SharedFlow<MainAction>
}

fun CoroutineScope.createMain(): Main {
    val events = MutableSharedFlow<MainAction>()

    return object : Main {
        override fun send(action: MainAction) {
            launch {
                events.emit(action)
            }
        }

        override val events: SharedFlow<MainAction> = events.asSharedFlow()
    }
}