package core.domain.usecase


import androidx.compose.runtime.Composable
import com.carpisoft.guau.SharedRes
import core.ui.model.ErrorUi
import core.utils.constants.Errors
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun GetMessageErrorUseCase(errorUi: ErrorUi): String {
    if (errorUi.error != null) {
        return when (errorUi.error) {
            Errors.BAD_CREDENTIALS -> {
                stringResource(SharedRes.strings.bad_credentials)
            }
            Errors.DUPLICATE_FIELD -> {
                when(errorUi.param){
                    "user_email_unique", "customer_email_unique"->{
                        stringResource(SharedRes.strings.field_duplicate_with_field, stringResource(SharedRes.strings.email).lowercase())
                    }
                    "customer_identification_unique"->{
                        stringResource(SharedRes.strings.field_duplicate_with_field, stringResource(SharedRes.strings.identification_number).lowercase())
                    }
                    else->{
                        ""
                    }
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