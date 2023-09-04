package splash.ui.screens

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import splash.domain.usecase.IsLoginTokenUseCase

class SplashViewModel(private val isLoginTokenUseCase: IsLoginTokenUseCase) : ViewModel() {

    private val _launchInitialSetup = MutableStateFlow(false)
    val launchInitialSetup: StateFlow<Boolean> = _launchInitialSetup

    private val _launchLogin = MutableStateFlow(false)
    val launchLogin: StateFlow<Boolean> = _launchLogin

    companion object{
        const val KEY = "SplashViewModel"
    }

    fun launchView() {
        viewModelScope.launch() {
            if (isLoginTokenUseCase()) {
                _launchInitialSetup.value = true
            } else {
                _launchLogin.value = true
            }
        }
    }

    fun resetLaunchLogin()
    {
        _launchLogin.value = false
    }

    fun resetLaunchInitialSetup()
    {
        _launchInitialSetup.value = false
    }
}