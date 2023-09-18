package ui

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import employee.domain.usecase.SaveCenterIdUseCase
import employee.domain.usecase.SaveEmployeeIdUseCase
import employee.domain.usecase.SaveRolUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import login.domain.usecase.SaveEmailUseCase
import login.domain.usecase.SaveNameUseCase
import login.domain.usecase.SaveTokenUseCase

class AppViewModel(
    private val saveEmailUseCase: SaveEmailUseCase,
    private val saveNameUseCase: SaveNameUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val saveEmployeeIdUseCase: SaveEmployeeIdUseCase,
    private val saveCenterIdUseCase: SaveCenterIdUseCase,
    private val saveRolUseCase: SaveRolUseCase
) : ViewModel() {

    companion object {
        const val KEY = "AppViewModel"
    }

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _showSignOffDialog = MutableStateFlow(false)
    val showSignOffDialog: StateFlow<Boolean> = _showSignOffDialog.asStateFlow()

    private val _showExitCenterDialog = MutableStateFlow(false)
    val showExitCenterDialog: StateFlow<Boolean> = _showExitCenterDialog.asStateFlow()

    private val _successSignOff = MutableStateFlow(false)
    val successSignOff: StateFlow<Boolean> = _successSignOff.asStateFlow()

    private val _successExitCenter = MutableStateFlow(false)
    val successExitCenter: StateFlow<Boolean> = _successExitCenter.asStateFlow()

    fun setTitle(title: String) {
        _title.value = title
    }

    fun resetSuccessExitCenter()
    {
        _successExitCenter.value = false
    }

    fun resetSuccessSignOff()
    {
        _successSignOff.value = false
    }

    fun showSignOffDialog(value: Boolean) {
        _showSignOffDialog.value = value
    }

    fun showExitCenterDialog(value: Boolean) {
        _showExitCenterDialog.value = value
    }

    fun signOff() {
        viewModelScope.launch(Dispatchers.IO) {
            saveTokenUseCase("")
            saveEmailUseCase("")
            saveNameUseCase("")
            saveCenterIdUseCase(-1)
            saveEmployeeIdUseCase(-1)
            saveRolUseCase("")
            _showSignOffDialog.value = false
            _successSignOff.value = true
        }
    }

    fun exitCenter() {
        viewModelScope.launch(Dispatchers.IO) {
            saveCenterIdUseCase(-1)
            saveEmployeeIdUseCase(-1)
            saveRolUseCase("")
            _showExitCenterDialog.value = false
            _successExitCenter.value = true
        }
    }

    var currentScreenRoute = ""
}