package com.carpisoft.guau.pet.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.core.ui.model.ErrorUi
import com.carpisoft.guau.employee.domain.usecase.GetCenterIdUseCase
import com.carpisoft.guau.login.domain.usecase.GetTokenUseCase
import com.carpisoft.guau.pet.domain.model.PetResp
import com.carpisoft.guau.pet.domain.usecase.GetPetsByCenterIdAndSearchWithPaginationAndSortUseCase
import com.carpisoft.guau.pet.domain.usecase.GetPetsByCenterIdWithPaginationAndSortUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PetsViewModel(
    private val getCenterIdUseCase: GetCenterIdUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val getPetsByCenterIdWithPaginationAndSortUseCase: GetPetsByCenterIdWithPaginationAndSortUseCase,
    private val getPetsByCenterIdAndSearchWithPaginationAndSortUseCase: GetPetsByCenterIdAndSearchWithPaginationAndSortUseCase

) : ViewModel() {

    companion object {
        const val TAG = "PetsViewModel"
    }

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _showError = MutableStateFlow(false)
    val showError: StateFlow<Boolean> = _showError.asStateFlow()

    private val _pets = MutableStateFlow(listOf<PetResp>())
    val pets: StateFlow<List<PetResp>> = _pets.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    private val _loadingMore = MutableStateFlow(false)
    val loadingMore: StateFlow<Boolean> = _loadingMore.asStateFlow()

    private val _showErrorSnackBar = MutableStateFlow(false)
    val showErrorSnackBar: StateFlow<Boolean> = _showErrorSnackBar.asStateFlow()

    var error: ErrorUi? = null
    var page = 0
    var limit = 15

    fun onLoadMorePetData() {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingMore.value = true
            val p = page + 1
            val resp = if (_searchText.value.isEmpty()) {
                getPetsByCenterIdWithPaginationAndSortUseCase(
                    token = getTokenUseCase(),
                    centerId = getCenterIdUseCase(),
                    page = p,
                    limit = limit
                )
            } else {
                getPetsByCenterIdAndSearchWithPaginationAndSortUseCase(
                    token = getTokenUseCase(),
                    centerId = getCenterIdUseCase(),
                    search = _searchText.value,
                    page = p,
                    limit = limit
                )
            }
            processMorePetsResult(resp)
            _loadingMore.value = false
        }
    }

    private fun processMorePetsResult(result: Resp<List<PetResp>>) {
        if (result.isValid) {
            val response = result.data!!
            if (response.isNotEmpty()) {
                val list = arrayListOf<PetResp>()
                for (pet in _pets.value) {
                    list.add(pet)
                }
                for (pet in response) {
                    list.add(pet)
                }
                page += 1
                _pets.value = list
            }
        } else {
            error = ErrorUi(error = result.error, code = result.errorCode)
            _showErrorSnackBar.value = true
        }
    }

    fun resetPets() {
        _pets.value = mutableListOf()
        page = 0
        _searchText.value = ""
    }

    fun onValueSearchPet(value: String) {
        if (value.length > 2 || _searchText.value.length == 3) {
            viewModelScope.launch(Dispatchers.IO) {
                page = 0
                val resp = if (value.length < 3) {
                    getPetsByCenterIdWithPaginationAndSortUseCase(
                        token = getTokenUseCase(),
                        centerId = getCenterIdUseCase(),
                        page = page,
                        limit = limit
                    )
                } else {
                    getPetsByCenterIdAndSearchWithPaginationAndSortUseCase(
                        token = getTokenUseCase(),
                        centerId = getCenterIdUseCase(),
                        search = value,
                        page = page,
                        limit = limit
                    )
                }
                processSearchPetsResult(resp)
            }
        }
        _searchText.value = value
    }

    private fun processSearchPetsResult(result: Resp<List<PetResp>>) {
        if (result.isValid) {
            val response = result.data!!
            _pets.value = response
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

    fun loadPetsData() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            val resp = getPetsByCenterIdWithPaginationAndSortUseCase(
                token = getTokenUseCase(),
                centerId = getCenterIdUseCase(),
                page = page,
                limit = limit
            )
            processPetsResult(resp)
            _loading.value = false
        }
    }

    private fun processPetsResult(result: Resp<List<PetResp>>) {
        if (result.isValid) {
            val response = result.data!!
            _pets.value = response
        } else {
            error = ErrorUi(error = result.error, code = result.errorCode)
            _showError.value = true
        }
    }

    fun resetShowErrorSnackBar() {
        _showErrorSnackBar.value = false
    }

    fun emptyValues() {
        _pets.value = mutableListOf()
        _searchText.value = ""
        error = null
        var page = 0
        var limit = 15
    }
}