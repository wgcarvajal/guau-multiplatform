package ui

import dev.icerock.moko.mvvm.viewmodel.ViewModel
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
    private val saveTokenUseCase: SaveTokenUseCase
) : ViewModel() {

    companion object {
        const val KEY = "AppViewModel"
    }

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _showSignOffDialog = MutableStateFlow(false)
    val showSignOffDialog: StateFlow<Boolean> = _showSignOffDialog.asStateFlow()

    private val _successSignOff = MutableStateFlow(false)
    val successSignOff: StateFlow<Boolean> = _successSignOff.asStateFlow()

    fun setTitle(title: String) {
        _title.value = title
    }

    fun showSignOffDialog(value: Boolean) {
        _showSignOffDialog.value = value
    }

    fun signOff() {
        viewModelScope.launch(Dispatchers.IO) {
            saveTokenUseCase("")
            saveEmailUseCase("")
            saveNameUseCase("")
            _showSignOffDialog.value = false
            _successSignOff.value = true
        }
    }

    var currentScreenRoute = ""
}