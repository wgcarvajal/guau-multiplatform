package com.carpisoft.guau.core.domain.usecase
class IsOnlyLettersUseCase() {
    operator fun invoke(value: String) = value.matches(Regex("[ña-zÑA-Z\\s]*"))
}