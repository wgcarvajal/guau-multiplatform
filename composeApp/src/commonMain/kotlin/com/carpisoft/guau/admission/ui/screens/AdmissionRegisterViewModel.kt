package com.carpisoft.guau.admission.ui.screens

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.carpisoft.guau.pet.domain.model.PetResp

class AdmissionRegisterViewModel : ViewModel() {

    companion object {
        const val KEY = "AdmissionRegisterViewModel"
        const val TAG = "AdmissionRegisterViewModel"
    }

    private val _petSelected = MutableStateFlow<PetResp?>(null)
    val petSelected: StateFlow<PetResp?> = _petSelected.asStateFlow()

    private val _enabledNextAction = MutableStateFlow(false)
    val enabledNextAction: StateFlow<Boolean> = _enabledNextAction.asStateFlow()

    var admissionType: AdmissionTypeEnum? = null


    fun selectedPet(petResp: PetResp){
        _petSelected.value = petResp
    }

    fun evaluatePetSelected() {
        _enabledNextAction.value = _petSelected.value != null
    }

    fun removeSelectedPet() {
        _petSelected.value = null
    }
}