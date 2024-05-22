package com.carpisoft.guau.pet.ui.screens

import androidx.compose.runtime.Composable
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.female
import guau.composeapp.generated.resources.male
import guau.composeapp.generated.resources.unknown
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun GetGenderPet(gender: String): String {
    return when (gender) {
        "Male" -> {
            stringResource(Res.string.male)
        }

        "Female" -> {
            stringResource(Res.string.female)
        }

        else -> {
            stringResource(Res.string.unknown)
        }
    }
}