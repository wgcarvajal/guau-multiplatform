package com.carpisoft.guau.pet.domain.model

data class BreedCompleteResp (
    val id: Int,
    val name: String,
    val image: String,
    val species: SpeciesResp,
    val state: Int
)