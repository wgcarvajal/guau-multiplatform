package com.carpisoft.guau.core.domain.usecase

import androidx.compose.runtime.Composable
import com.carpisoft.guau.core.ui.model.ErrorUi
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.bad_credentials
import guau.composeapp.generated.resources.email
import guau.composeapp.generated.resources.field_duplicate_with_field
import guau.composeapp.generated.resources.server_error
import org.jetbrains.compose.resources.stringResource

@Composable
fun GetMessageErrorBackendlessUseCase(errorUi: ErrorUi): String {
    if (errorUi.code != null) {
        return when (errorUi.code) {
            3003 -> stringResource(Res.string.bad_credentials)
            3033 -> stringResource(
                Res.string.field_duplicate_with_field,
                stringResource(Res.string.email).lowercase()
            )

            else -> ""
        }
    }
    return stringResource(Res.string.server_error)
}