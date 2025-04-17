package com.carpisoft.guau.pet.di

import com.carpisoft.guau.pet.data.network.repository.BreedRepository
import com.carpisoft.guau.pet.data.network.repository.GenderRepository
import com.carpisoft.guau.pet.data.network.repository.PetBackendlessRepository
import com.carpisoft.guau.pet.data.network.repository.SpeciesRepository
import com.carpisoft.guau.pet.domain.port.BreedPort
import com.carpisoft.guau.pet.domain.port.GenderPort
import com.carpisoft.guau.pet.domain.port.PetPort
import com.carpisoft.guau.pet.domain.port.SpeciesPort
import com.carpisoft.guau.pet.domain.usecase.GetBreedsBySpeciesIdAndNameWithPaginationAndSortUseCase
import com.carpisoft.guau.pet.domain.usecase.GetBreedsBySpeciesIdWithPaginationAndSortUseCase
import com.carpisoft.guau.pet.domain.usecase.GetGendersUseCase
import com.carpisoft.guau.pet.domain.usecase.GetPetsByCenterIdAndSearchWithPaginationAndSortUseCase
import com.carpisoft.guau.pet.domain.usecase.GetPetsByCenterIdWithPaginationAndSortUseCase
import com.carpisoft.guau.pet.domain.usecase.GetSpeciesUseCase
import com.carpisoft.guau.pet.domain.usecase.SavePetUseCase
import com.carpisoft.guau.pet.ui.screens.AddPetViewModel
import com.carpisoft.guau.pet.ui.screens.PetsViewModel
import com.carpisoft.guau.pet.ui.util.PetHelper
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val petModule: Module = module {
    factory<BreedPort> {
        BreedRepository(httpClient = get())
    }

    factory<GenderPort> {
        GenderRepository(httpClient = get())
    }

    factory<PetPort> {
        PetBackendlessRepository(httpClient = get())
    }

    factory<SpeciesPort> {
        SpeciesRepository(httpClient = get())
    }

    factory {
        GetBreedsBySpeciesIdAndNameWithPaginationAndSortUseCase(breedPort = get())
    }

    factory { GetBreedsBySpeciesIdWithPaginationAndSortUseCase(breedPort = get()) }

    factory { GetGendersUseCase(genderPort = get()) }

    factory { GetPetsByCenterIdAndSearchWithPaginationAndSortUseCase(petPort = get()) }

    factory { GetPetsByCenterIdWithPaginationAndSortUseCase(petPort = get()) }

    factory { GetSpeciesUseCase(speciesPort = get()) }

    factory { SavePetUseCase(petPort = get()) }

    single {
        PetHelper()
    }

    viewModelOf(::PetsViewModel)
    viewModelOf(::AddPetViewModel)

}