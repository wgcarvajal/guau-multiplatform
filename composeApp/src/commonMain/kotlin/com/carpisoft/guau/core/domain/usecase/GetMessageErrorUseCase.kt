package com.carpisoft.guau.core.domain.usecase

import androidx.compose.runtime.Composable
import com.carpisoft.guau.core.utils.constants.Errors
import com.carpisoft.guau.core.ui.model.ErrorUi
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.authentication_error
import guau.composeapp.generated.resources.bad_credentials
import guau.composeapp.generated.resources.email
import guau.composeapp.generated.resources.field_duplicate_with_field
import guau.composeapp.generated.resources.identification_number
import guau.composeapp.generated.resources.server_error
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
@OptIn(ExperimentalResourceApi::class)
@Composable
fun GetMessageErrorUseCase(errorUi: ErrorUi): String {
    if (errorUi.error != null) {
        return when (errorUi.error) {
            Errors.BAD_CREDENTIALS -> {
                stringResource(Res.string.bad_credentials)
            }

            Errors.DUPLICATE_FIELD -> {
                when (errorUi.param) {
                    "user_email_unique", "customer_email_unique" -> {
                        stringResource(
                            Res.string.field_duplicate_with_field,
                            stringResource(Res.string.email).lowercase()
                        )
                    }

                    "customer_identification_unique" -> {
                        stringResource(
                            Res.string.field_duplicate_with_field,
                            stringResource(Res.string.identification_number).lowercase()
                        )
                    }

                    else -> {
                        ""
                    }
                }
            }

            Errors.TOKEN_INVALID -> {
                stringResource(Res.string.authentication_error)
            }

            else -> {
                stringResource(Res.string.server_error)
            }
        }
    }
    return stringResource(Res.string.server_error)
}