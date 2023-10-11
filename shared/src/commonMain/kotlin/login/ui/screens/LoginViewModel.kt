package login.ui.screens


import core.domain.model.Resp
import core.ui.model.ErrorUi
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import login.domain.model.LoginReq
import login.domain.model.LoginResp
import login.domain.model.SocialLoginReq
import login.domain.usecase.DoLoginUseCase
import login.domain.usecase.DoSocialLoginUseCase
import login.domain.usecase.SaveEmailUseCase
import login.domain.usecase.SaveNameUseCase
import login.domain.usecase.SaveTokenUseCase
import core.domain.usecase.ValidateEmailAndPasswordUseCase
class LoginViewModel(
    private val validateEmailAndPasswordUseCase: ValidateEmailAndPasswordUseCase,
    private val doLoginUseCase: DoLoginUseCase,
    private val doSocialLoginUseCase: DoSocialLoginUseCase,
    private val saveEmailUseCase: SaveEmailUseCase,
    private val saveNameUseCase: SaveNameUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
) : ViewModel() {

    companion object {
        const val KEY = "LoginViewModel"
    }

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _loginEnabled = MutableStateFlow(false)
    val loginEnabled: StateFlow<Boolean> = _loginEnabled

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _showErrorDialog = MutableStateFlow(false)
    val showErrorDialog: StateFlow<Boolean> = _showErrorDialog

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    var error: ErrorUi? = null

    fun resetLoginSuccess() {
        _loginSuccess.value = false
    }

    fun onLoginChange(email: String, password: String) {
        _email.value = email
        _password.value = password
        _loginEnabled.value = validateEmailAndPasswordUseCase(email = email, password = password)
    }

    fun doLogin() {
        viewModelScope.launch() {
            _isLoading.value = true
            val result = doLoginUseCase(LoginReq(_email.value, _password.value))
            processResult(result)
        }
    }

    fun doSocialLogin(token: String, provider: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val result =
                doSocialLoginUseCase(SocialLoginReq(socialToken = token, provider = provider))
            processResult(result)
        }
    }

    private suspend fun processResult(result: Resp<LoginResp>) {
        if (result.isValid) {
            val response = result.data!!
            saveTokenUseCase(response.authorization)
            saveNameUseCase(response.name)
            saveEmailUseCase(response.email)
            _email.value = ""
            _password.value = ""
            _loginEnabled.value = false
            _loginSuccess.value = true
        } else {
            error = ErrorUi(error=result.error, code=result.errorCode)
            _showErrorDialog.value = true
        }
        _isLoading.value = false
    }

    fun getErrorUi(): ErrorUi? {
        return error
    }

    fun dismissErrorDialog() {
        _showErrorDialog.value = false
    }

}