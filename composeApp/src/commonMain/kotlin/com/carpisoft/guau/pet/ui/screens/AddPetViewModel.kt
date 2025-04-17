package com.carpisoft.guau.pet.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carpisoft.guau.core.domain.usecase.InitialsInCapitalLetterUseCase
import com.carpisoft.guau.core.domain.usecase.IsMaxStringSizeUseCase
import com.carpisoft.guau.core.domain.usecase.IsOnlyLettersUseCase
import com.carpisoft.guau.core.domain.usecase.RemoveInitialWhiteSpaceUseCase
import com.carpisoft.guau.core.domain.usecase.ValidateNameUseCase
import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.core.ui.model.ErrorUi
import com.carpisoft.guau.customer.domain.model.CustomerResp
import com.carpisoft.guau.customer.domain.model.IdentificationTypeResp
import com.carpisoft.guau.login.domain.usecase.GetTokenUseCase
import com.carpisoft.guau.pet.domain.model.BreedResp
import com.carpisoft.guau.pet.domain.model.GenderResp
import com.carpisoft.guau.pet.domain.model.PetReq
import com.carpisoft.guau.pet.domain.model.SpeciesResp
import com.carpisoft.guau.pet.domain.usecase.GetBreedsBySpeciesIdAndNameWithPaginationAndSortUseCase
import com.carpisoft.guau.pet.domain.usecase.GetBreedsBySpeciesIdWithPaginationAndSortUseCase
import com.carpisoft.guau.pet.domain.usecase.GetGendersUseCase
import com.carpisoft.guau.pet.domain.usecase.GetSpeciesUseCase
import com.carpisoft.guau.pet.domain.usecase.SavePetUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddPetViewModel(
    private val getSpeciesUseCase: GetSpeciesUseCase,
    private val getBreedsBySpeciesIdWithPaginationAndSortUseCase: GetBreedsBySpeciesIdWithPaginationAndSortUseCase,
    private val getBreedsBySpeciesIdAndNameWithPaginationAndSortUseCase: GetBreedsBySpeciesIdAndNameWithPaginationAndSortUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val getGendersUseCase: GetGendersUseCase,
    private val isOnlyLettersUseCase: IsOnlyLettersUseCase,
    private val isMaxStringSizeUseCase: IsMaxStringSizeUseCase,
    private val removeInitialWhiteSpaceUseCase: RemoveInitialWhiteSpaceUseCase,
    private val initialsInCapitalLetterUseCase: InitialsInCapitalLetterUseCase,
    private val validateNameUseCase: ValidateNameUseCase,
    private val savePetUseCase: SavePetUseCase
) : ViewModel() {

    companion object {
        const val TAG = "AddPetViewModel"
    }

    private val _enabledNextAction = MutableStateFlow(false)
    val enabledNextAction: StateFlow<Boolean> = _enabledNextAction.asStateFlow()

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _loadingMore = MutableStateFlow(false)
    val loadingMore: StateFlow<Boolean> = _loadingMore.asStateFlow()

    private val _showError = MutableStateFlow(false)
    val showError: StateFlow<Boolean> = _showError.asStateFlow()

    private val _showErrorSnackBar = MutableStateFlow(false)
    val showErrorSnackBar: StateFlow<Boolean> = _showErrorSnackBar.asStateFlow()

    private val _typePets = MutableStateFlow(listOf<SpeciesResp>())
    val typePets: StateFlow<List<SpeciesResp>> = _typePets.asStateFlow()

    private val _breeds = MutableStateFlow(listOf<BreedResp>())
    val breeds: StateFlow<List<BreedResp>> = _breeds.asStateFlow()

    private val _typePetSelected =
        MutableStateFlow(SpeciesResp(id = "", name = "", image = "", state = -1))
    val typePetSelected: StateFlow<SpeciesResp> = _typePetSelected.asStateFlow()

    private val _showSuccessDialog = MutableStateFlow(false)
    val showSuccessDialog: StateFlow<Boolean> = _showSuccessDialog

    private val _customerSelected =
        MutableStateFlow(
            CustomerResp(
                id = "",
                identificationType = IdentificationTypeResp(id = -1, name = ""),
                identification = "",
                name = "",
                lastName = ""
            )
        )
    val customerSelected: StateFlow<CustomerResp> = _customerSelected.asStateFlow()

    private val _breedSelected =
        MutableStateFlow(BreedResp(id = -1, name = "", image = "", state = -1))
    val breedSelected: StateFlow<BreedResp> = _breedSelected.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    private val _birthdate = MutableStateFlow("")
    val birthdate: StateFlow<String> = _birthdate.asStateFlow()

    private val _genderSelected = MutableStateFlow(GenderResp(-1, ""))
    val genderSelected: StateFlow<GenderResp> =
        _genderSelected.asStateFlow()

    private val _genders = MutableStateFlow(listOf<GenderResp>())
    val genders: StateFlow<List<GenderResp>> =
        _genders.asStateFlow()

    var error: ErrorUi? = null
    var page = 0
    var limit = 15

    fun loadSpeciesData() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            val resp = getSpeciesUseCase(token = getTokenUseCase())
            processSpeciesResult(resp)
            _loading.value = false
        }
    }

    fun loadBreedData() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            val resp = getBreedsBySpeciesIdWithPaginationAndSortUseCase(
                token = getTokenUseCase(),
                speciesId = _typePetSelected.value.id.toInt(),
                page = page,
                limit = limit
            )
            processBreedResult(resp)
            _loading.value = false
        }
    }

    private fun processSpeciesResult(result: Resp<List<SpeciesResp>>) {
        if (result.isValid) {
            val response = result.data!!
            _typePets.value = response
        } else {
            error = ErrorUi(error = result.error, code = result.errorCode)
            _showError.value = true
        }
    }

    private fun processBreedResult(result: Resp<List<BreedResp>>) {
        if (result.isValid) {
            val response = result.data!!
            _breeds.value = response
        } else {
            error = ErrorUi(error = result.error, code = result.errorCode)
            _showError.value = true
        }
    }

    private fun processMoreBreedResult(result: Resp<List<BreedResp>>) {
        if (result.isValid) {
            val response = result.data!!
            if (response.isNotEmpty()) {
                val list = arrayListOf<BreedResp>()
                for (breed in _breeds.value) {
                    list.add(breed)
                }
                for (breed in response) {
                    list.add(breed)
                }
                page += 1
                _breeds.value = list
            }
        } else {
            error = ErrorUi(error = result.error, code = result.errorCode)
            _showErrorSnackBar.value = true
        }
    }

    private fun processSearchBreedResult(result: Resp<List<BreedResp>>) {
        if (result.isValid) {
            val response = result.data!!
            _breeds.value = response
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

    fun selectedTypePet(typePet: SpeciesResp) {
        _typePetSelected.value = typePet
    }

    fun selectedBreed(breed: BreedResp) {
        _breedSelected.value = breed
    }

    fun setName(name: String) {
        _name.value = name
    }

    fun setDescription(description: String) {
        _description.value = description
    }

    fun selectedCustomer(customer: CustomerResp) {
        _customerSelected.value = customer
    }

    fun removeSelectedTypePet() {
        emptyValues()
    }

    fun removeSelectedBreed() {
        _breedSelected.value = BreedResp(id = -1, name = "", image = "", state = -1)
    }


    fun onValueSearchBreed(value: String) {
        if (value.length > 2 || _searchText.value.length == 3) {
            viewModelScope.launch(Dispatchers.IO) {
                page = 0
                val resp = if (value.length < 3) {
                    getBreedsBySpeciesIdWithPaginationAndSortUseCase(
                        token = getTokenUseCase(),
                        speciesId = _typePetSelected.value.id.toInt(),
                        page = page,
                        limit = limit
                    )
                } else {
                    getBreedsBySpeciesIdAndNameWithPaginationAndSortUseCase(
                        token = getTokenUseCase(),
                        speciesId = _typePetSelected.value.id.toInt(),
                        name = value,
                        page = page,
                        limit = limit
                    )
                }
                processSearchBreedResult(resp)
            }
        }
        _searchText.value = value
    }


    fun onLoadMoreBreedData() {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingMore.value = true
            val p = page + 1
            val resp = if (_searchText.value.isEmpty()) {
                getBreedsBySpeciesIdWithPaginationAndSortUseCase(
                    token = getTokenUseCase(),
                    speciesId = _typePetSelected.value.id.toInt(),
                    page = p,
                    limit = limit
                )
            } else {
                getBreedsBySpeciesIdAndNameWithPaginationAndSortUseCase(
                    token = getTokenUseCase(),
                    speciesId = _typePetSelected.value.id.toInt(),
                    name = _searchText.value,
                    page = p,
                    limit = limit
                )
            }
            processMoreBreedResult(resp)
            _loadingMore.value = false
        }
    }

    fun resetBreeds() {
        _breeds.value = mutableListOf()
        page = 0
        _searchText.value = ""
    }

    fun resetShowErrorSnackBar() {
        _showErrorSnackBar.value = false
    }

    fun resetCustomerSelected() {
        _customerSelected.value = CustomerResp(
            id = "",
            identificationType = IdentificationTypeResp(id = -1, name = ""),
            identification = "",
            name = "",
            lastName = ""
        )
        validateEnabledNext()
    }

    fun emptyValues() {
        _typePets.value = mutableListOf()
        _birthdate.value = ""
        _name.value = ""
        _description.value = ""
        _genders.value = mutableListOf()
        _breeds.value = mutableListOf()
        _typePetSelected.value = SpeciesResp(id = "", name = "", image = "", state = -1)
        _breedSelected.value = BreedResp(-1, "", "", 0)
        _customerSelected.value = CustomerResp(
            id = "",
            identificationType = IdentificationTypeResp(id = -1, name = ""),
            identification = "",
            name = "",
            lastName = ""
        )
        _genderSelected.value = GenderResp(-1, "")
    }

    fun onSelectedGender(value: GenderResp) {
        _genderSelected.value = value
        validateEnabledNext()
    }

    fun loadGendersData() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            val resp = getGendersUseCase(
                token = getTokenUseCase()
            )
            processGendersResult(resp)
            _loading.value = false
        }
    }

    fun onChangeName(value: String): String {
        if (isOnlyLettersUseCase(value) && isMaxStringSizeUseCase(value, 40)) {
            var nameFormat = initialsInCapitalLetterUseCase(removeInitialWhiteSpaceUseCase(value))
            _name.value = nameFormat
            validateEnabledNext()
        }
        return _name.value
    }

    fun onChangeDescription(value: String): String {
        if (isMaxStringSizeUseCase(value, 100)) {
            var nameFormat = initialsInCapitalLetterUseCase(removeInitialWhiteSpaceUseCase(value))
            _description.value = nameFormat
        }
        return _description.value
    }

    private fun processGendersResult(result: Resp<List<GenderResp>>) {
        if (result.isValid) {
            val response = result.data!!
            _genders.value = response
        } else {
            error = ErrorUi(error = result.error, code = result.errorCode)
            _showError.value = true
        }
    }

    fun changeBirthdate(value: String) {
        _birthdate.value = value
        validateEnabledNext()
    }

    fun validateEnabledNext() {
        _enabledNextAction.value =
            _customerSelected.value.id != "" &&
                    _birthdate.value.isNotEmpty() &&
                    validateNameUseCase(_name.value) && _genderSelected.value.id > 0
    }

    fun save() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            val resp = savePetUseCase(
                token = getTokenUseCase(), petReq = PetReq(
                    date = _birthdate.value.toLong(), name = _name.value,
                    description = if (_description.value.isNotEmpty()) {
                        _description.value
                    } else {
                        null
                    },
                    breed = _breedSelected.value.id,
                    customer = _customerSelected.value.id.toLong(),
                    gender = _genderSelected.value.id
                )
            )
            processPetSaveResult(resp)
            _loading.value = false
        }
    }

    private fun processPetSaveResult(result: Resp<Long>) {
        if (result.isValid) {
            _showSuccessDialog.value = true
        } else {
            error = ErrorUi(error = result.error, code = result.errorCode)
            _showError.value = true
        }
    }

    fun dismissSuccessDialog() {
        _showSuccessDialog.value = false
    }

}