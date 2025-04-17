package com.carpisoft.guau.pet.ui.util

import com.carpisoft.guau.customer.domain.model.CustomerResp
import com.carpisoft.guau.pet.domain.model.BreedResp
import com.carpisoft.guau.pet.domain.model.GenderResp
import com.carpisoft.guau.pet.domain.model.SpeciesResp

class PetHelper {
    private var typePetSelected: SpeciesResp? = null
    private var breedPetSelected: BreedResp? = null
    private var name: String = ""
    private var description: String = ""
    private var birthdate: String = ""
    private var genderSelected: GenderResp? = null
    private var customerSelected: CustomerResp? = null

    fun emptyValues() {
        typePetSelected = null
        breedPetSelected = null
        name = ""
        description = ""
        birthdate = ""
        genderSelected = null
        customerSelected = null
    }

    fun setTypePetSelected(speciesResp: SpeciesResp?) {
        typePetSelected = speciesResp
    }

    fun getTypePetSelected(): SpeciesResp? = typePetSelected

    fun setBreedPetSelected(breedResp: BreedResp?) {
        breedPetSelected = breedResp
    }

    fun getBreedPetSelected(): BreedResp? = breedPetSelected

    fun setName(name: String) {
        this.name = name
    }

    fun getName(): String = name

    fun setDescription(description: String) {
        this.description = description
    }

    fun getDescription(): String = description

    fun setBirthdate(birthdate: String) {
        this.birthdate = birthdate
    }

    fun getBirthdate(): String = birthdate

    fun setGenderSelected(genderSelected: GenderResp?) {
        this.genderSelected = genderSelected
    }

    fun getGenderSelected(): GenderResp? = genderSelected

    fun setCustomerSelected(customerResp: CustomerResp?) {
        customerSelected = customerResp
    }

    fun getCustomerSelected(): CustomerResp? = customerSelected
}