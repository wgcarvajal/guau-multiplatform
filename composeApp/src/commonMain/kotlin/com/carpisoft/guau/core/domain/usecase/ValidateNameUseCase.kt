package com.carpisoft.guau.core.domain.usecase

class ValidateNameUseCase {
    operator fun invoke(name: String): Boolean {
        return name.isNotEmpty() && name.length >= 2
    }
}