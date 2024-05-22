package com.carpisoft.guau.pet.domain.model

import com.carpisoft.guau.customer.domain.model.CustomerResp
import com.carpisoft.guau.pet.domain.model.BreedCompleteResp
import com.carpisoft.guau.pet.domain.model.GenderResp

data class PetResp(
    val id: Long,
    val date: Long,
    val name: String,
    val description: String?,
    val breed: BreedCompleteResp,
    val customer: CustomerResp,
    val gender: GenderResp
)
