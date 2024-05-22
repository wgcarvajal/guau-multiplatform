package com.carpisoft.guau.pet.ui.screens

import androidx.compose.runtime.Composable
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.cat
import guau.composeapp.generated.resources.dog
import guau.composeapp.generated.resources.other
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun GetNamePetKind(name: String): String {
    return when (name) {
        "Dog" -> {
            stringResource(Res.string.dog)
        }

        "Cat" -> {
            stringResource(Res.string.cat)
        }

        else -> {
            stringResource(Res.string.other)
        }
    }
}
