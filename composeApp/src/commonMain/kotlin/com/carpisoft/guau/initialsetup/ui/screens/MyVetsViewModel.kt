package com.carpisoft.guau.initialsetup.ui.screens

import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.core.ui.model.ErrorUi
import com.carpisoft.guau.initialsetup.domain.model.EmployeeResp
import com.carpisoft.guau.initialsetup.domain.model.EmployeesReq
import com.carpisoft.guau.initialsetup.domain.usecase.GetEmployeesUseCase
import com.carpisoft.guau.initialsetup.domain.usecase.SaveCenterIdAndRolUseCase
import com.carpisoft.guau.login.domain.usecase.GetEmailUseCase
import com.carpisoft.guau.login.domain.usecase.GetTokenUseCase
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class MyVetsViewModel(
    private val getTokenUseCase: GetTokenUseCase,
    private val getEmailUseCase: GetEmailUseCase,
    private val getEmployeesUseCase: GetEmployeesUseCase,
    private val saveCenterIdAndRolUseCase: SaveCenterIdAndRolUseCase
) : ViewModel() {

    companion object {
        const val KEY = "MyVetsViewModel"
        const val TAG = "MyVetsViewModel"
    }

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _isEmpty = MutableStateFlow(false)
    val isEmpty: StateFlow<Boolean> = _isEmpty.asStateFlow()

    private val _showError = MutableStateFlow(false)
    val showError: StateFlow<Boolean> = _showError.asStateFlow()

    private val _list = MutableStateFlow((listOf<EmployeeResp>()))
    val list: StateFlow<List<EmployeeResp>> = _list.asStateFlow()

    private val _goHome = MutableStateFlow(false)
    val goHome: StateFlow<Boolean> = _goHome.asStateFlow()

    var error: ErrorUi? = null


    fun showMyVets() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            val resp = getEmployeesUseCase(
                token = getTokenUseCase(),
                employeesReq = EmployeesReq(email = getEmailUseCase(), rol = "owner")
            )
            processResult(resp)
            _loading.value = false
        }
    }

    private fun processResult(result: Resp<List<EmployeeResp>>) {
        if (result.isValid) {
            val response = result.data!!
            if (response.isEmpty()) {
                _isEmpty.value = true
            } else {
                _isEmpty.value = false
                _list.value = response
            }
        } else {
            error = ErrorUi(error = result.error, code = result.errorCode)
            _showError.value = true
        }
    }

    fun getErrorUi(): ErrorUi? {
        return error
    }

    fun dismissErrorDialog() {
        _showError.value = false
    }

    fun resetGoHome() {
        _goHome.value = false
    }

    fun selectVet(employeeResp: EmployeeResp) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            saveCenterIdAndRolUseCase(
                employeeId = employeeResp.idEmployee,
                centerId = employeeResp.centerResp.id,
                rol = "owner"
            )
            _loading.value = false
            _goHome.value = true
        }
    }
}