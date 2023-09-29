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
import pet.domain.usecase.GetBreedsBySpeciesIdWithPaginationAndSortUseCase
import pet.domain.usecase.GetSpeciesUseCase

class AddPetViewModel(
    private val getSpeciesUseCase: GetSpeciesUseCase,
    private val getBreedsBySpeciesIdWithPaginationAndSortUseCase: GetBreedsBySpeciesIdWithPaginationAndSortUseCase,
    private val getTokenUseCase: GetTokenUseCase
) : ViewModel() {

    companion object {
        const val KEY = "SelectPetTypeViewModel"
    }

    private val _enabledNextAction = MutableStateFlow(false)
    val enabledNextAction: StateFlow<Boolean> = _enabledNextAction.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _showError = MutableStateFlow(false)
    val showError: StateFlow<Boolean> = _showError.asStateFlow()


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
                page = 0,
                limit = 15
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

    fun onValueSearchBreed(value:String){
        _searchText.value = value
    }
}