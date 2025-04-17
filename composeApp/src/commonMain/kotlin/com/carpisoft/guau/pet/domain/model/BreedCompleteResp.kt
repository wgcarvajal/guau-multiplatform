package com.carpisoft.guau.pet.domain.model

data class BreedCompleteResp (
    val id: String,
    val name: String,
    val image: String,
    val species: SpeciesResp,
    val state: Int
)