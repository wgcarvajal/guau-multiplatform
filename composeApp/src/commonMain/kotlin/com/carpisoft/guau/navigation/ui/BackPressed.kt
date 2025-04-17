package com.carpisoft.guau.navigation.ui

import cafe.adriel.voyager.core.screen.Screen
import com.carpisoft.guau.main.ui.screens.MainScreen
import com.carpisoft.guau.navigation
import com.carpisoft.guau.navigation.ui.states.NavigationAction
import com.carpisoft.guau.pet.ui.screens.SelectPetTypeScreen


fun backPressed(currentScreen: Screen): Boolean {
    return when (currentScreen.key) {
        MainScreen.TAG -> {
            false
        }

        SelectPetTypeScreen.TAG->{
            navigation.send(action = NavigationAction.BackHandler())
            false
        }

        else -> {
            true
        }
    }
}