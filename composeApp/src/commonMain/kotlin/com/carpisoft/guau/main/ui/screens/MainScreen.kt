package com.carpisoft.guau.main.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.carpisoft.guau.admission.ui.screens.AdmissionScreen
import com.carpisoft.guau.core.ui.screens.scaffold.GuauScaffoldTab
import com.carpisoft.guau.home.ui.HomeTab
import com.carpisoft.guau.main.ui.states.NavigateScreenListener

class MainScreen : Screen {

    companion object{
        const val TAG = "MainScreen"
    }

    override val key: ScreenKey
        get() = TAG
    @Composable
    override fun Content() {
        val navigator: Navigator? = LocalNavigator.current
        Screen()
        NavigateScreenListener { mainAction ->
            when (mainAction.param) {
                AdmissionScreen.TAG -> {
                    navigator?.push(item = AdmissionScreen())
                }
            }
        }
    }

    @Composable
    private fun Screen() {
        TabNavigator(
            tab = HomeTab,
            tabDisposable = {
                TabDisposable(
                    navigator = it,
                    tabs = listOf(HomeTab)
                )
            },
            content = { tabNavigator ->
                GuauScaffoldTab(
                    tabNavigator = tabNavigator,
                    title = tabNavigator.current.options.title,
                    showExitCenter = true,
                    showAddActionButton = false,
                    showAccountOptions = true,
                    onClickAddActionButton = {},
                    signOffOnClick = {},
                    onExitVet = {}
                ) { paddingValues ->
                    Box(modifier = Modifier.fillMaxSize().padding(paddingValues = paddingValues)) {
                        CurrentTab()
                    }
                }
            }
        )
    }
}