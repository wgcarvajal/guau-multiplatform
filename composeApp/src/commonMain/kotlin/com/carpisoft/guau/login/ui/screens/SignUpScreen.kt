package com.carpisoft.guau.login.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.carpisoft.guau.core.domain.usecase.GetMessageErrorUseCase
import com.carpisoft.guau.core.ui.model.ErrorUi
import com.carpisoft.guau.core.ui.model.UiStructureProperties
import com.carpisoft.guau.core.ui.screens.buttons.GeneralButton
import com.carpisoft.guau.core.ui.screens.dialogs.OneButtonDialog
import com.carpisoft.guau.core.ui.screens.loading.SimpleLoading
import com.carpisoft.guau.core.ui.screens.text.TextQuestionWithLink
import com.carpisoft.guau.core.ui.screens.textfields.SimpleTextField
import com.carpisoft.guau.core.ui.screens.textfields.SimpleTextFieldPassword
import guau.composeapp.generated.resources.Res
import guau.composeapp.generated.resources.already_a_member
import guau.composeapp.generated.resources.email
import guau.composeapp.generated.resources.last_name
import guau.composeapp.generated.resources.login
import guau.composeapp.generated.resources.name
import guau.composeapp.generated.resources.password
import guau.composeapp.generated.resources.sign_up
import guau.composeapp.generated.resources.successful_registration
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

class SignUpScreen:Screen{
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val signUpViewModel:SignUpViewModel = GetSignUpViewModel()
        val onNameChange: (String) -> Unit = {
            signUpViewModel.nameChange(it)
        }
        val onLastNameChange: (String) -> Unit = {
            signUpViewModel.lastNameChange(it)
        }
        val onEmailChange: (String) -> Unit = {
            signUpViewModel.emailChange(it)
        }
        val onPasswordChange: (String) -> Unit = {
            signUpViewModel.passwordChange(it)
        }

        val onClick: () -> Unit = {
            signUpViewModel.signUp()
        }
        val name by signUpViewModel.name.collectAsState()
        val lastName by signUpViewModel.lastName.collectAsState()
        val email by signUpViewModel.email.collectAsState()
        val password by signUpViewModel.password.collectAsState()
        val enableButton by signUpViewModel.enableButton.collectAsState()
        val showLoading by signUpViewModel.showLoading.collectAsState()
        if (showLoading) {
            SimpleLoading()
        } else {
            Screen(
                name = name,
                lastName = lastName,
                email = email,
                password = password,
                enableButton = enableButton,
                onNameChange = onNameChange,
                onLastNameChange = onLastNameChange,
                onEmailChange = onEmailChange,
                onPasswordChange = onPasswordChange,
                onClickLink = {
                    navigator?.pop()
                },
                onClick = onClick
            )
        }
    }

}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SignUpScreen(
    uiStructureProperties: UiStructureProperties,
    signUpViewModel: SignUpViewModel,
    onClickLink: () -> Unit
) {
    LaunchedEffect(key1 = 1) {
        uiStructureProperties.onShowTopBar(false)
        uiStructureProperties.onShowBottomBar(false)
        uiStructureProperties.showAddActionButton(false)
    }

    val onNameChange: (String) -> Unit = {
        signUpViewModel.nameChange(it)
    }
    val onLastNameChange: (String) -> Unit = {
        signUpViewModel.lastNameChange(it)
    }
    val onEmailChange: (String) -> Unit = {
        signUpViewModel.emailChange(it)
    }
    val onPasswordChange: (String) -> Unit = {
        signUpViewModel.passwordChange(it)
    }

    val onClick: () -> Unit = {
        signUpViewModel.signUp()
    }
    val name by signUpViewModel.name.collectAsState()
    val lastName by signUpViewModel.lastName.collectAsState()
    val email by signUpViewModel.email.collectAsState()
    val password by signUpViewModel.password.collectAsState()
    val enableButton by signUpViewModel.enableButton.collectAsState()
    val showLoading by signUpViewModel.showLoading.collectAsState()
    if (showLoading) {
        SimpleLoading()
    } else {
        Screen(
            name = name,
            lastName = lastName,
            email = email,
            password = password,
            enableButton = enableButton,
            onNameChange = onNameChange,
            onLastNameChange = onLastNameChange,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            onClickLink = onClickLink,
            onClick = onClick
        )
    }
    val resetValues by signUpViewModel.resetValues.collectAsState()
    LaunchedEffect(key1 = resetValues)
    {
        if (resetValues) {
            signUpViewModel.resetValues(false)
            signUpViewModel.initValues()
        }
    }

    LaunchedEffect(key1 = 1) {
        if (name.isNotEmpty() || lastName.isNotEmpty() || email.isNotEmpty() || password.isNotEmpty() || enableButton) {
            signUpViewModel.initValues()
        }
    }

    val showErrorDialog by signUpViewModel.showErrorDialog.collectAsState()
    OneButtonDialog(
        show = showErrorDialog,
        message = GetMessageErrorUseCase(
            signUpViewModel.error ?: ErrorUi()
        ),
        onDismissRequest = { signUpViewModel.dismissErrorDialog() })

    val showSuccessDialog by signUpViewModel.showSuccessDialog.collectAsState()
    OneButtonDialog(
        show = showSuccessDialog,
        message = stringResource(Res.string.successful_registration),
        onDismissRequest = {
            signUpViewModel.dismissSuccessDialog()
            onClickLink()
        })
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun Screen(
    name: String,
    lastName: String,
    email: String,
    password: String,
    enableButton: Boolean,
    onNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onClickLink: () -> Unit,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize().padding(start = 8.dp, end = 8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SimpleTextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                placeholder = "",
                label = "${stringResource(Res.string.name)} *",
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                onValueChange = onNameChange
            )
            Spacer(modifier = Modifier.height(8.dp))
            SimpleTextField(
                modifier = Modifier.fillMaxWidth(),
                value = lastName,
                placeholder = "",
                label = "${stringResource(Res.string.last_name)} *",
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                onValueChange = onLastNameChange
            )
            Spacer(modifier = Modifier.height(8.dp))
            SimpleTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                placeholder = "",
                label = "${stringResource(Res.string.email)} *",
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
                onValueChange = onEmailChange
            )
            Spacer(modifier = Modifier.height(8.dp))
            SimpleTextFieldPassword(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                placeholder = "",
                label = "${stringResource(Res.string.password)} *",
                onValueChange = onPasswordChange
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextQuestionWithLink(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.already_a_member),
                link = stringResource(Res.string.login),
                onClickLink = onClickLink
            )
            Spacer(modifier = Modifier.height(24.dp))
            GeneralButton(
                modifier = Modifier.width(200.dp),
                label = stringResource(Res.string.sign_up),
                enabled = enableButton,
                onClick = onClick
            )
        }
    }
}

