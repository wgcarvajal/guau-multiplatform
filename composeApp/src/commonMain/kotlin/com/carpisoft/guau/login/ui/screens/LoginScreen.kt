package com.carpisoft.guau.login.ui.screens

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.carpisoft.guau.core.domain.usecase.GetMessageErrorBackendlessUseCase
import com.carpisoft.guau.core.domain.usecase.GetMessageErrorUseCase
import com.carpisoft.guau.core.ui.model.ErrorUi
import com.carpisoft.guau.core.ui.model.UiStructureProperties
import com.carpisoft.guau.core.ui.screens.buttons.GeneralButton
import com.carpisoft.guau.core.ui.screens.dialogs.OneButtonDialog
import com.carpisoft.guau.core.ui.screens.loading.SimpleLoading
import com.carpisoft.guau.core.ui.screens.text.TextQuestionWithLink
import com.carpisoft.guau.core.ui.screens.textfields.SimpleTextField
import com.carpisoft.guau.core.ui.screens.textfields.SimpleTextFieldPassword
import com.carpisoft.guau.initialsetup.ui.screens.InitialScreen
import com.carpisoft.guau.login.ui.states.LoginWithGoogleHandler
import com.carpisoft.guau.login.ui.states.SocialLoginAction
import com.carpisoft.guau.socialLogin
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.app_name_shared
import guau.composeapp.generated.resources.dont_have_an_account
import guau.composeapp.generated.resources.email
import guau.composeapp.generated.resources.enter_your_email
import guau.composeapp.generated.resources.enter_your_password
import guau.composeapp.generated.resources.password
import guau.composeapp.generated.resources.sign_in
import guau.composeapp.generated.resources.sign_up
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class LoginScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val loginViewModel = koinViewModel<LoginViewModel>()
        val email by loginViewModel.email.collectAsState()
        val password by loginViewModel.password.collectAsState()
        val loginEnabled by loginViewModel.loginEnabled.collectAsState()
        val isLoading by loginViewModel.isLoading.collectAsState()
        if (isLoading) {
            SimpleLoading()
        } else {
            Screen(
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
                    socialLogin.send(action = SocialLoginAction.GoLoginWithGoogle())
                },
                onClickSignUp = {
                    navigator?.push(item = SignUpScreen())
                }
            )
        }
        val showErrorDialog by loginViewModel.showErrorDialog.collectAsState()
        OneButtonDialog(
            show = showErrorDialog,
            message = GetMessageErrorBackendlessUseCase(loginViewModel.getErrorUi() ?: ErrorUi()),
            onDismissRequest = { loginViewModel.dismissErrorDialog() })

        val loginSuccess by loginViewModel.loginSuccess.collectAsState()
        if (loginSuccess) {
            loginViewModel.resetLoginSuccess()
            navigator?.replace(item = InitialScreen())
        }
        LoginWithGoogleHandler {
            loginViewModel.doSocialLogin(it.param, "Google")
        }
    }

}

@Composable
fun LoginScreen(
    uiStructureProperties: UiStructureProperties,
    loginViewModel: LoginViewModel,
    onClickSignUp: () -> Unit,
    loginSuccess: () -> Unit,
    loginWithGoogle: () -> Unit
) {
    LaunchedEffect(key1 = 1) {
        uiStructureProperties.onShowTopBar(false)
        uiStructureProperties.onShowBottomBar(false)
        uiStructureProperties.showAddActionButton(false)
    }
    val email by loginViewModel.email.collectAsState()
    val password by loginViewModel.password.collectAsState()
    val loginEnabled by loginViewModel.loginEnabled.collectAsState()
    val isLoading by loginViewModel.isLoading.collectAsState()
    if (isLoading) {
        SimpleLoading()
    } else {
        Screen(
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
                loginWithGoogle()
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
        loginViewModel.resetLoginSuccess()
        loginSuccess()
    }

    LoginWithGoogleHandler {
        loginViewModel.doSocialLogin(it.param, "Google")
    }
}

@Composable
private fun Screen(
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
                    text = stringResource(Res.string.app_name_shared),
                    fontSize = 36.sp,
                )
                SimpleTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 16.dp),
                    value = email,
                    placeholder = stringResource(Res.string.enter_your_email),
                    label = stringResource(Res.string.email),
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
                    placeholder = stringResource(Res.string.enter_your_password),
                    label = stringResource(Res.string.password),
                    onValueChange = onPasswordValueChange
                )

                GeneralButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 16.dp),
                    label = stringResource(Res.string.sign_in),
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
                text = stringResource(Res.string.dont_have_an_account),
                link = "${stringResource(Res.string.sign_up)}."
            ) {
                onClickSignUp()
            }
        }
    }
}