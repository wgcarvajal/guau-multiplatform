package customer.ui

import core.domain.model.Resp
import core.domain.usecase.InitialsInCapitalLetterUseCase
import core.domain.usecase.IsMaxStringSizeUseCase
import core.domain.usecase.IsOnlyLettersUseCase
import core.domain.usecase.IsOnlyNumbersUseCase
import core.domain.usecase.RemoveInitialWhiteSpaceUseCase
import core.domain.usecase.ValidateEmailUseCase
import core.domain.usecase.ValidateNameUseCase
import core.ui.model.ErrorUi
import customer.domain.model.RegisterCustomerReq
import customer.domain.model.RegisterCustomerResp
import customer.domain.model.IdentificationTypeResp
import customer.domain.usecase.GetAllIdentificationTypeUseCase
import customer.domain.usecase.SaveCustomerUseCase
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import employee.domain.usecase.GetCenterIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import login.domain.usecase.GetTokenUseCase

class AddCustomerViewModel(
    private val getAllIdentificationTypeUseCase: GetAllIdentificationTypeUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val isOnlyLettersUseCase: IsOnlyLettersUseCase,
    private val isOnlyNumbersUseCase: IsOnlyNumbersUseCase,
    private val isMaxStringSizeUseCase: IsMaxStringSizeUseCase,
    private val initialsInCapitalLetterUseCase: InitialsInCapitalLetterUseCase,
    private val removeInitialWhiteSpaceUseCase: RemoveInitialWhiteSpaceUseCase,
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val saveCustomerUseCase: SaveCustomerUseCase,
    private val getCenterIdUseCase: GetCenterIdUseCase
) :
    ViewModel() {

    companion object {
        const val KEY = "AddCustomerViewModel"
    }

    private val _identificationTypes = MutableStateFlow(listOf<IdentificationTypeResp>())
    val identificationTypes: StateFlow<List<IdentificationTypeResp>> =
        _identificationTypes.asStateFlow()

    private val _identificationTypeSelected = MutableStateFlow(IdentificationTypeResp(-1, ""))
    val identificationTypeSelected: StateFlow<IdentificationTypeResp> =
        _identificationTypeSelected.asStateFlow()

    private val _identificationNumber = MutableStateFlow("")
    val identificationNumber: StateFlow<String> = _identificationNumber.asStateFlow()

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String> = _lastName.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> = _phone.asStateFlow()

    private val _address = MutableStateFlow("")
    val address: StateFlow<String> = _address.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _showError = MutableStateFlow(false)
    val showError: StateFlow<Boolean> = _showError.asStateFlow()

    private val _enabledSave = MutableStateFlow(false)
    val enabledSave: StateFlow<Boolean> = _enabledSave.asStateFlow()

    private val _showErrorSnackBar = MutableStateFlow(false)
    val showErrorSnackBar: StateFlow<Boolean> = _showErrorSnackBar.asStateFlow()

    private val _showSuccessDialog = MutableStateFlow(false)
    val showSuccessDialog: StateFlow<Boolean> = _showSuccessDialog

    var error: ErrorUi? = null

    fun getErrorUi(): ErrorUi? {
        return error
    }

    fun dismissErrorDialog() {
        _showError.value = false
    }

    fun loadIdentificationTypeData() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            val resp = getAllIdentificationTypeUseCase(
                token = getTokenUseCase()
            )
            processResult(resp)
            _loading.value = false
        }
    }

    private fun processResult(result: Resp<List<IdentificationTypeResp>>) {
        if (result.isValid) {
            val response = result.data!!
            _identificationTypes.value = response
        } else {
            error = ErrorUi(error = result.error, code = result.errorCode)
            _showError.value = true
        }
    }

    fun onSelectedIdentificationType(identificationTypeResp: IdentificationTypeResp) {
        _identificationTypeSelected.value = identificationTypeResp
        validateEnabledSave()
    }

    fun onChangeIdentificationNumber(value: String) {
        if (isOnlyNumbersUseCase(value) && isMaxStringSizeUseCase(value, 15)) {
            _identificationNumber.value = value
            validateEnabledSave()
        }
    }

    fun onChangeName(value: String) {
        if (isOnlyLettersUseCase(value) && isMaxStringSizeUseCase(value, 40)) {
            var nameFormat = initialsInCapitalLetterUseCase(removeInitialWhiteSpaceUseCase(value))
            _name.value = nameFormat
            validateEnabledSave()
        }
    }

    fun onChangeLastName(value: String) {
        if (isOnlyLettersUseCase(value) && isMaxStringSizeUseCase(value, 40)) {
            var nameFormat = initialsInCapitalLetterUseCase(removeInitialWhiteSpaceUseCase(value))
            _lastName.value = nameFormat
            validateEnabledSave()
        }
    }

    fun onChangeEmail(value: String) {
        if (isMaxStringSizeUseCase(value, 100)) {
            _email.value = value
            validateEnabledSave()
        }
    }

    fun onChangePhone(value: String) {
        if (isOnlyNumbersUseCase(value) && isMaxStringSizeUseCase(value, 15)) {
            _phone.value = value
        }
    }

    fun onChangeAddress(value: String) {
        if (isMaxStringSizeUseCase(value, 100)) {
            _address.value = value
        }
    }

    private fun validateEnabledSave() {
        _enabledSave.value =
            identificationTypeSelected.value.id > 0 &&
                    _identificationNumber.value.isNotEmpty() &&
                    validateNameUseCase(_name.value) &&
                    validateNameUseCase(_lastName.value) &&
                    (_email.value.isEmpty() || validateEmailUseCase(_email.value))
    }

    fun save() {
        viewModelScope.launch(Dispatchers.IO) {
            _enabledSave.value = false
            _loading.value = true
            val resp = saveCustomerUseCase(
                getTokenUseCase(), RegisterCustomerReq(
                    center = getCenterIdUseCase().toInt(),
                    identificationType = _identificationTypeSelected.value.id,
                    identification = _identificationNumber.value,
                    name = _name.value,
                    lastName = _lastName.value,
                    email = if (_email.value.isEmpty()) {
                        null
                    } else {
                        _email.value
                    },
                    address = if (_address.value.isEmpty()) {
                        null
                    } else {
                        _address.value
                    },
                    phone = if (_phone.value.isEmpty()) {
                        null
                    } else {
                        _phone.value
                    }
                )
            )
            processSaveCustomerResult(result = resp)
            _enabledSave.value = true
            _loading.value = false
        }
    }

    private fun processSaveCustomerResult(result: Resp<RegisterCustomerResp>) {
        if (result.isValid) {
            emptyValues()
            _showSuccessDialog.value = true

        } else {
            error = ErrorUi(error = result.error, param = result.param, code = result.errorCode)
            _showErrorSnackBar.value = true
        }
    }

    fun emptyValues()
    {
        _identificationTypeSelected.value = IdentificationTypeResp(-1, "")
        _identificationNumber.value = ""
        _name.value = ""
        _lastName.value = ""
        _email.value = ""
        _address.value = ""
        _phone.value = ""
    }
    fun resetShowErrorSnackBar() {
        _showErrorSnackBar.value = false
    }

    fun dismissSuccessDialog() {
        _showSuccessDialog.value = false
    }
}