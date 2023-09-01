package core.domain.usecase


import androidx.compose.runtime.Composable
import com.myapplication.SharedRes
import core.ui.model.ErrorUi
import core.utils.constants.Errors
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun GetMessageErrorUseCase(errorUi: ErrorUi, field: String? = null): String {
    if (errorUi.error != null) {
        return when (errorUi.error) {
            Errors.BAD_CREDENTIALS -> {
                stringResource(SharedRes.strings.bad_credentials)
            }
            Errors.DUPLICATE_FIELD -> {
                if (field != null) {
                    stringResource(SharedRes.strings.field_duplicate_with_field, field.lowercase())
                } else {
                    stringResource(SharedRes.strings.field_duplicate)
                }
            }
            Errors.TOKEN_INVALID -> {
                stringResource(SharedRes.strings.authentication_error)
            }
            else -> {
                stringResource(SharedRes.strings.server_error)
            }
        }
    }
    return stringResource(SharedRes.strings.server_error)
}