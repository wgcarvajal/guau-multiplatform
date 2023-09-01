package login.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapplication.SharedRes
import core.domain.usecase.GetMessageErrorUseCase
import core.ui.model.ErrorUi
import core.ui.screens.buttons.GeneralButton
import core.ui.screens.dialogs.OneButtonDialog
import core.ui.screens.loading.SimpleLoading
import core.ui.screens.text.TextQuestionWithLink
import core.ui.screens.textfields.SimpleTextField
import core.ui.screens.textfields.SimpleTextFieldPassword
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun LoginScreen(loginViewModel: LoginViewModel, onClickSignUp: () -> Unit) {
    val email by loginViewModel.email.collectAsState()
    val password by loginViewModel.password.collectAsState()
    val loginEnabled by loginViewModel.loginEnabled.collectAsState()
    val isLoading by loginViewModel.isLoading.collectAsState()
    if (isLoading) {
        SimpleLoading()
    } else {
        ScreenPortrait(
            email = email,
            password = password,
            enabled = loginEnabled,
            onEmailValueChange = {
                loginViewModel.onLoginChange(email = it, password = password)
            },
            onPasswordValueChange = {
                loginViewModel.onLoginChange(email = email, password = it)
            },
            onClickSignIn = {
                loginViewModel.doLogin()
            },
            onClickGoogle = {

            },
            onClickSignUp = onClickSignUp
        )
    }

    val showErrorDialog by loginViewModel.showErrorDialog.collectAsState()
    OneButtonDialog(
        show = showErrorDialog,
        message = GetMessageErrorUseCase(loginViewModel.getErrorUi() ?: ErrorUi()),
        onDismissRequest = { loginViewModel.dismissErrorDialog() })

    val loginSuccess by loginViewModel.loginSuccess.collectAsState()
    if (loginSuccess) {
        //launchActivities.launchInitialSetupActivityAndCloseCurrent()
    }
}

@Composable
private fun ScreenPortrait(
    email: String,
    password: String,
    enabled: Boolean,
    onEmailValueChange: (String) -> Unit,
    onPasswordValueChange: (String) -> Unit,
    onClickSignIn: () -> Unit,
    onClickGoogle: () -> Unit,
    onClickSignUp: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.weight(10f)) {
            Column(modifier = Modifier.fillMaxWidth().align(Alignment.Center)) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    text = stringResource(SharedRes.strings.app_name),
                    fontSize = 36.sp,
                )
                SimpleTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 16.dp),
                    value = email,
                    placeholder = stringResource(SharedRes.strings.enter_your_email),
                    label = stringResource(SharedRes.strings.email),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Email
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Email,
                            contentDescription = "email icon"
                        )
                    },
                    onValueChange = onEmailValueChange
                )

                SimpleTextFieldPassword(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 16.dp),
                    value = password,
                    placeholder = stringResource(SharedRes.strings.enter_your_password),
                    label = stringResource(SharedRes.strings.password),
                    onValueChange = onPasswordValueChange
                )

                GeneralButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 16.dp),
                    label = stringResource(SharedRes.strings.sign_in),
                    enabled = enabled,
                    onClick = onClickSignIn
                )
                GeneralButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 16.dp),
                    label = "Google",
                    enabled = true,
                    onClick = onClickGoogle
                )
            }
        }
        Box(modifier = Modifier.weight(1.5f)) {
            Divider(modifier = Modifier.align(Alignment.TopStart))
            TextQuestionWithLink(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(SharedRes.strings.dont_have_an_account),
                link = "${stringResource(SharedRes.strings.sign_up)}."
            ) {
                onClickSignUp()
            }
        }
    }
}