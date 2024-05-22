package com.carpisoft.guau.login.ui.screens

import com.carpisoft.guau.core.domain.usecase.InitialsInCapitalLetterUseCase
import com.carpisoft.guau.core.domain.usecase.IsMaxStringSizeUseCase
import com.carpisoft.guau.core.domain.usecase.IsOnlyLettersUseCase
import com.carpisoft.guau.core.domain.usecase.RemoveInitialWhiteSpaceUseCase
import com.carpisoft.guau.core.domain.usecase.ValidateEmailAndPasswordUseCase
import com.carpisoft.guau.core.domain.usecase.ValidateNameUseCase
import com.carpisoft.guau.core.ui.model.ErrorUi
import com.carpisoft.guau.login.domain.model.SignUpReq
import com.carpisoft.guau.login.domain.usecase.DoRegisterUseCase
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class SignUpViewModel(
    private val doRegisterUseCase: DoRegisterUseCase,
    private val initialsInCapitalLetterUseCase: InitialsInCapitalLetterUseCase,
    private val removeInitialWhiteSpaceUseCase: RemoveInitialWhiteSpaceUseCase,
    private val isOnlyLettersUseCase: IsOnlyLettersUseCase,
    private val isMaxStringSizeUseCase: IsMaxStringSizeUseCase,
    private val validateEmailAndPasswordUseCase: ValidateEmailAndPasswordUseCase,
    private val validateNameUseCase: ValidateNameUseCase
) : ViewModel() {

    companion object {
        const val KEY = "SignUpViewModel"
        const val TAG = "SignUpViewModel"
    }

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String> = _lastName

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _enableButton = MutableStateFlow(false)
    val enableButton: StateFlow<Boolean> = _enableButton

    private val _resetValues = MutableStateFlow(false)
    val resetValues: StateFlow<Boolean> = _resetValues

    private val _showLoading = MutableStateFlow(false)
    val showLoading: StateFlow<Boolean> = _showLoading

    private val _showErrorDialog = MutableStateFlow(false)
    val showErrorDialog: StateFlow<Boolean> = _showErrorDialog

    private val _showSuccessDialog = MutableStateFlow(false)
    val showSuccessDialog: StateFlow<Boolean> = _showSuccessDialog

    var error: ErrorUi? = null

    fun nameChange(name: String) {
        if (isOnlyLettersUseCase(name) && isMaxStringSizeUseCase(name, 40)) {
            var nameFormat = initialsInCapitalLetterUseCase(removeInitialWhiteSpaceUseCase(name))
            _name.value = nameFormat
            _enableButton.value = validateNameUseCase(_name.value) &&
                    validateNameUseCase(_lastName.value) &&
                    validateEmailAndPasswordUseCase(_email.value, _password.value)
        }
    }

    fun lastNameChange(lastName: String) {
        if (isOnlyLettersUseCase(lastName) && isMaxStringSizeUseCase(lastName, 40)) {
            var lastNameFormat =
                initialsInCapitalLetterUseCase(removeInitialWhiteSpaceUseCase(lastName))
            _lastName.value = lastNameFormat
            _enableButton.value = validateNameUseCase(_name.value) &&
                    validateNameUseCase(_lastName.value) &&
                    validateEmailAndPasswordUseCase(_email.value, _password.value)
        }
    }

    fun emailChange(email: String) {
        _email.value = email
        _enableButton.value = validateNameUseCase(_name.value) &&
                validateNameUseCase(_lastName.value) &&
                validateEmailAndPasswordUseCase(_email.value, _password.value)
    }

    fun passwordChange(password: String) {
        _password.value = password
        _enableButton.value = validateNameUseCase(_name.value) &&
                validateNameUseCase(_lastName.value) &&
                validateEmailAndPasswordUseCase(_email.value, _password.value)
    }

    fun initValues() {
        _name.value = ""
        _lastName.value = ""
        _email.value = ""
        _password.value = ""
        _enableButton.value = false
    }

    fun signUp() {
        _showLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val result = doRegisterUseCase(
                SignUpReq(
                    email = _email.value,
                    password = _password.value,
                    name = _name.value,
                    lastName = _lastName.value
                )
            )
            if (result.isValid) {
                initValues()
                _showSuccessDialog.value = true
            } else {
                error = ErrorUi(error = result.error, param = result.param, code = result.errorCode)
                _showErrorDialog.value = true
            }
            _showLoading.value = false
        }
    }

    fun resetValues(value: Boolean) {
        _resetValues.value = value
    }


    fun dismissErrorDialog() {
        _showErrorDialog.value = false
    }

    fun dismissSuccessDialog() {
        _showSuccessDialog.value = false
    }
}