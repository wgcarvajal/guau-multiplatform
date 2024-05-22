package com.carpisoft.guau.customer.ui

import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.core.ui.model.ErrorUi
import com.carpisoft.guau.customer.domain.model.CustomerResp
import com.carpisoft.guau.customer.domain.usecase.GetCustomersByCenterIdAndNameWithPaginationAndSortUseCase
import com.carpisoft.guau.customer.domain.usecase.GetCustomersByCenterIdWithPaginationAndSortUseCase
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import com.carpisoft.guau.employee.domain.usecase.GetCenterIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.carpisoft.guau.login.domain.usecase.GetTokenUseCase

class CustomerViewModel(
    private val getCustomersByCenterIdWithPaginationAndSortUseCase: GetCustomersByCenterIdWithPaginationAndSortUseCase,
    private val getCustomersByCenterIdAndNameWithPaginationAndSortUseCase: GetCustomersByCenterIdAndNameWithPaginationAndSortUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val getCenterIdUseCase: GetCenterIdUseCase
) : ViewModel() {

    companion object {
        const val KEY = "CustomerViewModel"
        const val TAG = "CustomerViewModel"
    }

    private val _enabledNextAction = MutableStateFlow(false)
    val enabledNextAction: StateFlow<Boolean> = _enabledNextAction.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _loadingMore = MutableStateFlow(false)
    val loadingMore: StateFlow<Boolean> = _loadingMore.asStateFlow()

    private val _showError = MutableStateFlow(false)
    val showError: StateFlow<Boolean> = _showError.asStateFlow()

    private val _showErrorSnackBar = MutableStateFlow(false)
    val showErrorSnackBar: StateFlow<Boolean> = _showErrorSnackBar.asStateFlow()

    private val _customers = MutableStateFlow(listOf<CustomerResp>())
    val customers: StateFlow<List<CustomerResp>> = _customers.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    var error: ErrorUi? = null
    var page = 0
    var limit = 15


    fun loadCustomerData() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            val resp = getCustomersByCenterIdWithPaginationAndSortUseCase(
                token = getTokenUseCase(),
                centerId = getCenterIdUseCase().toInt(),
                page = page,
                limit = limit
            )
            processCustomersResult(resp)
            _loading.value = false
        }
    }

    private fun processCustomersResult(result: Resp<List<CustomerResp>>) {
        if (result.isValid) {
            val response = result.data!!
            _customers.value = response
        } else {
            error = ErrorUi(error = result.error, code = result.errorCode)
            _showError.value = true
        }
    }

    private fun processMoreCustomerResult(result: Resp<List<CustomerResp>>) {
        if (result.isValid) {
            val response = result.data!!
            if (response.isNotEmpty()) {
                val list = arrayListOf<CustomerResp>()
                for (customer in _customers.value) {
                    list.add(customer)
                }
                for (customer in response) {
                    list.add(customer)
                }
                page += 1
                _customers.value = list
            }
        } else {
            error = ErrorUi(error = result.error, code = result.errorCode)
            _showErrorSnackBar.value = true
        }
    }

    private fun processSearchCustomerResult(result: Resp<List<CustomerResp>>) {
        if (result.isValid) {
            val response = result.data!!
            _customers.value = response
        } else {
            error = ErrorUi(error = result.error, code = result.errorCode)
            _showErrorSnackBar.value = true
        }
    }

    fun getErrorUi(): ErrorUi? {
        return error
    }

    fun dismissErrorDialog() {
        _showError.value = false
    }

    fun onValueSearchCustomer(value: String) {
        if (value.length > 2 || _searchText.value.length == 3) {
            viewModelScope.launch(Dispatchers.IO) {
                page = 0
                val resp = if (value.length < 3) {
                    getCustomersByCenterIdWithPaginationAndSortUseCase(
                        token = getTokenUseCase(),
                        centerId = getCenterIdUseCase().toInt(),
                        page = page,
                        limit = limit
                    )
                } else {
                    getCustomersByCenterIdAndNameWithPaginationAndSortUseCase(
                        token = getTokenUseCase(),
                        centerId = getCenterIdUseCase().toInt(),
                        search = value,
                        page = page,
                        limit = limit
                    )
                }
                processSearchCustomerResult(resp)
            }
        }
        _searchText.value = value
    }


    fun onLoadMoreBreedData() {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingMore.value = true
            val p = page + 1
            val resp = if (_searchText.value.isEmpty()) {
                getCustomersByCenterIdWithPaginationAndSortUseCase(
                    token = getTokenUseCase(),
                    centerId = getCenterIdUseCase().toInt(),
                    page = p,
                    limit = limit
                )
            } else {
                getCustomersByCenterIdAndNameWithPaginationAndSortUseCase(
                    token = getTokenUseCase(),
                    centerId = getCenterIdUseCase().toInt(),
                    search = _searchText.value,
                    page = p,
                    limit = limit
                )
            }
            processMoreCustomerResult(resp)
            _loadingMore.value = false
        }
    }

    fun resetCustomers() {
        _customers.value = mutableListOf()
        page = 0
        _searchText.value = ""
    }

    fun resetShowErrorSnackBar() {
        _showErrorSnackBar.value = false
    }

    fun emptyValues() {
        _customers.value = mutableListOf()
    }
}