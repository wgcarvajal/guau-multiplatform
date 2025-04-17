package com.carpisoft.guau.admission.ui.screens

import androidx.lifecycle.ViewModel
import com.carpisoft.guau.pet.domain.model.PetResp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AdmissionRegisterViewModel : ViewModel() {

    companion object {
        const val KEY = "AdmissionRegisterViewModel"
        const val TAG = "AdmissionRegisterViewModel"
    }

    private val _petSelected = MutableStateFlow<PetResp?>(null)
    val petSelected: StateFlow<PetResp?> = _petSelected.asStateFlow()


    var admissionType: AdmissionTypeEnum? = null

    fun selectedPet(petResp: PetResp) {
        _petSelected.value = petResp
    }

    fun removeSelectedPet() {
        _petSelected.value = null
    }
}