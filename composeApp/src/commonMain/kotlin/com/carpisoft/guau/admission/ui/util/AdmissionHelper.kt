package com.carpisoft.guau.admission.ui.util

import com.carpisoft.guau.pet.domain.model.PetResp

class AdmissionHelper {
    private var petSelected: PetResp? = null

    fun setPetSelected(petResp: PetResp?) {
        petSelected = petResp
    }

    fun getPetSelected(): PetResp? = petSelected
}