package pet.ui.screens

import core.domain.model.Resp
import core.ui.model.ErrorUi
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import login.domain.usecase.GetTokenUseCase
import pet.domain.model.BreedResp
import pet.domain.model.SpeciesResp
import pet.domain.usecase.GetBreedsBySpeciesIdAndNameWithPaginationAndSortUseCase
import pet.domain.usecase.GetBreedsBySpeciesIdWithPaginationAndSortUseCase
import pet.domain.usecase.GetSpeciesUseCase

class AddPetViewModel(
    private val getSpeciesUseCase: GetSpeciesUseCase,
    private val getBreedsBySpeciesIdWithPaginationAndSortUseCase: GetBreedsBySpeciesIdWithPaginationAndSortUseCase,
    private val getBreedsBySpeciesIdAndNameWithPaginationAndSortUseCase: GetBreedsBySpeciesIdAndNameWithPaginationAndSortUseCase,
    private val getTokenUseCase: GetTokenUseCase
) : ViewModel() {

    companion object {
        const val KEY = "AddPetViewModel"
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

    private val _typePets = MutableStateFlow(listOf<SpeciesResp>())
    val typePets: StateFlow<List<SpeciesResp>> = _typePets.asStateFlow()

    private val _breeds = MutableStateFlow(listOf<BreedResp>())
    val breeds: StateFlow<List<BreedResp>> = _breeds.asStateFlow()

    private val _typePetSelected =
        MutableStateFlow(SpeciesResp(id = -1, name = "", image = "", state = -1))
    val typePetSelected: StateFlow<SpeciesResp> = _typePetSelected.asStateFlow()

    private val _breedSelected =
        MutableStateFlow(BreedResp(id = -1, name = "", image = "", state = -1))
    val breedSelected: StateFlow<BreedResp> = _breedSelected.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

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
                speciesId = _typePetSelected.value.id,
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
            error = ErrorUi(result.error, result.errorCode)
            _showError.value = true
        }
    }

    private fun processBreedResult(result: Resp<List<BreedResp>>) {
        if (result.isValid) {
            val response = result.data!!
            _breeds.value = response
        } else {
            error = ErrorUi(result.error, result.errorCode)
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
            error = ErrorUi(result.error, result.errorCode)
            _showErrorSnackBar.value = true
        }
    }

    private fun processSearchBreedResult(result: Resp<List<BreedResp>>) {
        if (result.isValid) {
            val response = result.data!!
            _breeds.value = response
        } else {
            error = ErrorUi(result.error, result.errorCode)
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

    fun removeSelectedTypePet() {
        _typePetSelected.value = SpeciesResp(id = -1, name = "", image = "", state = -1)
        _breedSelected.value = BreedResp(id = -1, name = "", image = "", state = -1)
    }

    fun removeSelectedBreed() {
        _breedSelected.value = BreedResp(id = -1, name = "", image = "", state = -1)
    }

    fun evaluateSpeciesSelected() {
        _enabledNextAction.value = _typePetSelected.value.id != -1
    }

    fun evaluateBreedSelected() {
        _enabledNextAction.value = _breedSelected.value.id != -1
    }

    fun onValueSearchBreed(value: String) {
        if (value.length > 2 || _searchText.value.length == 3) {
            viewModelScope.launch(Dispatchers.IO) {
                page = 0
                val resp = if (value.length < 3) {
                    getBreedsBySpeciesIdWithPaginationAndSortUseCase(
                        token = getTokenUseCase(),
                        speciesId = _typePetSelected.value.id,
                        page = page,
                        limit = limit
                    )
                } else {
                    getBreedsBySpeciesIdAndNameWithPaginationAndSortUseCase(
                        token = getTokenUseCase(),
                        speciesId = _typePetSelected.value.id,
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
                    speciesId = _typePetSelected.value.id,
                    page = p,
                    limit = limit
                )
            } else {
                getBreedsBySpeciesIdAndNameWithPaginationAndSortUseCase(
                    token = getTokenUseCase(),
                    speciesId = _typePetSelected.value.id,
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

}