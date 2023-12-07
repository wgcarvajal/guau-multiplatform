package splash.ui.screens

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import splash.domain.usecase.IsLoginTokenUseCase
import splash.domain.usecase.IsSelectedVetUseCase

class SplashViewModel(
    private val isLoginTokenUseCase: IsLoginTokenUseCase,
    private val isSelectedVetUseCase: IsSelectedVetUseCase
) : ViewModel() {

    private val _launchInitialSetup = MutableStateFlow(false)
    val launchInitialSetup: StateFlow<Boolean> = _launchInitialSetup

    private val _launchLogin = MutableStateFlow(false)
    val launchLogin: StateFlow<Boolean> = _launchLogin

    private val _launchHome = MutableStateFlow(false)
    val launchHome: StateFlow<Boolean> = _launchHome

    companion object {
        const val KEY = "SplashViewModel"
    }

    fun launchView() {
        viewModelScope.launch() {
            if(isSelectedVetUseCase()){
                _launchHome.value = true
            }
            else if (isLoginTokenUseCase()) {
                _launchInitialSetup.value = true
            } else {
                _launchLogin.value = true
            }
        }
    }

    fun resetLaunchLogin() {
        _launchLogin.value = false
    }

    fun resetLaunchInitialSetup() {
        _launchInitialSetup.value = false
    }

    fun resetLaunchHome() {
        _launchHome.value = false
    }
}